package com.example.gamedetails.service;

import com.example.gamedetails.adapter.MatchAdapter;
import com.example.gamedetails.adapter.TeamMemberAdapter;
import com.example.gamedetails.models.dto.broker.MatchResultMsgDto;
import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.enums.MatchStatus;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMatchScore;
import com.example.gamedetails.models.orm.TeamMember;
import com.example.gamedetails.publish.FinishedMatchesPublisher;
import com.example.gamedetails.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
//@Slf4j
public class FetchingService {
    @Autowired
    private MatchAdapter matchAdapter;

    @Autowired
    private TeamMemberAdapter teamMemberAdapter;

    @Autowired
    private MatchJpaRepo matchJpaRepo;

    @Autowired
    private GameJpaRepo gameJpaRepo;

    @Autowired
    private TeamJpaRepo teamJpaRepo;

    @Autowired
    private TeamMemberJpaRepo teamMemberJpaRepo;

    @Autowired
    private TeamMatchScoreJpaRepo teamMatchScoreJpaRepo;

    @Autowired
    private FinishedMatchesPublisher gateway;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void fetchMatches() {
//        log.info("FetchingService.fetchMatches");
        fetchMatchesByGame(GamesNames.LOL);
        fetchMatchesByGame(GamesNames.DOTA2);
        fetchMatchesByGame(GamesNames.CODMW);
    }

    public List<TeamMember> fetchTeamByGameAndId(GamesNames gameName, Integer id) {
        var restTemplate = new RestTemplate();
        // todo optimise query to pandora
        ResponseEntity<String> response = restTemplate.getForEntity(
                String.format("https://api.pandascore.co/%s/teams?token=Y7PK0TAUPBIxNCK2OKIUR4Btj0BPkzd74-Yv9GN88P2PCDd_hQo&filter[id]=%s",
                        gameName.name().toLowerCase(), id),
                String.class);

        return teamMemberAdapter.jsonToOrm(response.getBody());
    }

    public void fetchMatchesByGame(GamesNames gameName) {
        var restTemplate = new RestTemplate();
        // todo optimise query to pandora
        ResponseEntity<String> response = restTemplate.getForEntity("https://api.pandascore.co/" + gameName.name().toLowerCase() + "/matches?token=D7pVhsHNRdbnETpxVeN3UnSb7oHGQokBzSdECxkm-gov5JIELdE", String.class);

        List<PandoraMatchesDto> pandoraMatchesDtos = matchAdapter.jsonToPandoraMatchesDto(response.getBody());

//        log.info("{} {}", gameName.name().toLowerCase(), pandoraMatchesDtos.size());
//        int i = 0;
        for (var matchItem : pandoraMatchesDtos) {
//            log.info("{}", matchItem);
            matchItem.getPandoraTeamScoreDtos().forEach(pandoraTeamScoreDto -> {
                if (pandoraTeamScoreDto.getAcronym() == null || pandoraTeamScoreDto.getAcronym().equals("null")) {
                    pandoraTeamScoreDto.setAcronym(findAcronym(pandoraTeamScoreDto.getName()));
                }
            });
//            log.info("{}", matchItem);
            var matchOrm = matchAdapter.pandoraMatchToTeamMatchOrm(matchItem);
            matchOrm.setGame(gameJpaRepo.getById(gameName));
            var matchExists = matchJpaRepo.existsByPandoraIdIsAndNameIs(matchOrm.getPandoraId(), matchOrm.getName());
//            log.info("{}: {} {} exists:{}", i++, matchOrm.getPandoraId(), matchOrm.getName(), matchExists);
            if (!matchExists) {
                matchJpaRepo.saveAndFlush(matchOrm);

                matchItem.getPandoraTeamScoreDtos().forEach(pandoraTeamScoreDto -> {
                    Optional<Team> teamExists = teamJpaRepo.findByAcronymAndName(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName());
                    if (teamExists.isEmpty()) {
                        var team = new Team(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName(), pandoraTeamScoreDto.getImageUrl());
                        var teamMembers = fetchTeamByGameAndId(gameName, pandoraTeamScoreDto.getId());
                        teamJpaRepo.saveAndFlush(team);
                        teamMembers.forEach(teamMember -> teamMember.setTeam(team));
                        teamMemberJpaRepo.saveAllAndFlush(teamMembers);
                        teamExists = Optional.of(team);
                    }

                    var teamMatchScore = matchAdapter.pandoraTeamsToOrmTeams(pandoraTeamScoreDto, matchOrm, teamExists.get());
                    teamMatchScoreJpaRepo.saveAndFlush(teamMatchScore);
                });

                if (matchOrm.getStatus() == MatchStatus.FINISHED) {
                    var dbMatch = matchJpaRepo.queryByPandoraIdIsAndNameIs(matchOrm.getPandoraId(), matchOrm.getName());
                    sendMatchResult(dbMatch);
                }
            } else {
                var dbMatch = matchJpaRepo.queryByPandoraIdIsAndNameIs(matchOrm.getPandoraId(), matchOrm.getName());
//                log.info("{} {} vs {} {}", matchOrm.getPandoraId(), matchOrm.getStatus(), dbMatch.getPandoraId(), dbMatch.getStatus());

                if (matchOrm.getStatus() == MatchStatus.FINISHED && dbMatch.getStatus() != MatchStatus.FINISHED) {
                    dbMatch.getTeams().forEach(teamMatchScore ->
                            teamMatchScore.setScore(matchItem.getPandoraTeamScoreDtos().stream().filter(pandoraTeamScoreDto -> pandoraTeamScoreDto.getAcronym().equals(teamMatchScore.getTeam().getAcronym())).findFirst().get().getScore()));

                    teamMatchScoreJpaRepo.saveAllAndFlush(dbMatch.getTeams());
                    dbMatch.setStatus(MatchStatus.FINISHED);
                    matchJpaRepo.saveAndFlush(dbMatch);
                    sendMatchResult(dbMatch);
                }
            }
        }
    }

    private void sendMatchResult(com.example.gamedetails.models.orm.Match matchOrm) {
        var teams = matchOrm.getTeams().stream().sorted(Comparator.comparingLong(TeamMatchScore::getScore)).collect(Collectors.toList());

        if (teams.get(teams.size() - 1).getScore() == teams.get(teams.size() - 2).getScore()) {
            sendMsg(matchOrm.getId(), -1L);
        }

        sendMsg(matchOrm.getId(), Long.valueOf(teams.get(teams.size() - 1).getTeam().getId()));
    }

    private void sendMsg(long matchId, Long teamId) {
        gateway.publish(new MatchResultMsgDto(matchId, teamId));
    }

    // todo delete old matches

    public static void main(String[] args) {
        findAcronym("Dragon");
    }

    private static String findAcronym(String name) {
        var p = Pattern.compile("((^| )[A-Za-z])");
        var m = p.matcher(name);
        var initials = new StringBuilder();
        while (m.find()) {
            initials.append(m.group().trim());
        }
        if (initials.length() == 1) {
            initials.setLength(0);
            initials.append(name, 0, Math.min(3, name.length()));
        }
        return initials.toString().trim().toUpperCase();
    }
}
