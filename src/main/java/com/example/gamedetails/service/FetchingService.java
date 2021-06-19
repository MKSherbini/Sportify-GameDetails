package com.example.gamedetails.service;

import com.example.gamedetails.adapter.MatchAdapter;
import com.example.gamedetails.models.dto.broker.MatchResultMsgDto;
import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.enums.MatchStatus;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMatchScore;
import com.example.gamedetails.publish.FinishedMatchesPublisher;
import com.example.gamedetails.repos.GameJpaRepo;
import com.example.gamedetails.repos.MatchJpaRepo;
import com.example.gamedetails.repos.TeamJpaRepo;
import com.example.gamedetails.repos.TeamMatchScoreJpaRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FetchingService {
    @Autowired
    private MatchAdapter matchAdapter;

    @Autowired
    private MatchJpaRepo matchJpaRepo;

    @Autowired
    private GameJpaRepo gameJpaRepo;

    @Autowired
    private TeamJpaRepo teamJpaRepo;

    @Autowired
    private TeamMatchScoreJpaRepo teamMatchScoreJpaRepo;

    @Autowired
    private FinishedMatchesPublisher gateway;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void fetchMatches() {
        log.info("FetchingService.fetchMatches");
        fetchMatchesByGame(GamesNames.LOL);
        fetchMatchesByGame(GamesNames.DOTA2);
        fetchMatchesByGame(GamesNames.CODMW);
    }

    public void fetchMatchesByGame(GamesNames gameName) {
        var restTemplate = new RestTemplate();
        // todo optimise query to pandora
        ResponseEntity<String> response = restTemplate.getForEntity("https://api.pandascore.co/" + gameName.name().toLowerCase() + "/matches?token=D7pVhsHNRdbnETpxVeN3UnSb7oHGQokBzSdECxkm-gov5JIELdE", String.class);

        List<PandoraMatchesDto> pandoraMatchesDtos = null;
        try {
            pandoraMatchesDtos = matchAdapter.jsonToPandoraMatchesDto(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        log.info("{} {}", gameName.name().toLowerCase(), pandoraMatchesDtos.size());
        int i = 0;
        for (var matchItem : pandoraMatchesDtos) {
            var matchOrm = matchAdapter.pandoraMatchToTeamMatchOrm(matchItem);
            matchOrm.setGame(gameJpaRepo.getById(gameName));
            var matchExists = matchJpaRepo.existsByPandoraIdIsAndNameIs(matchOrm.getPandoraId(), matchOrm.getName());
            log.info("{}: {} {} exists:{}", i++, matchOrm.getPandoraId(), matchOrm.getName(), matchExists);
            if (!matchExists) {
                matchJpaRepo.saveAndFlush(matchOrm);

                matchItem.getPandoraTeamScoreDtos().forEach(pandoraTeamScoreDto -> {
                    Optional<Team> teamExists = teamJpaRepo.findByAcronymAndName(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName());
                    if (teamExists.isEmpty()) {
                        var team = new Team(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName());
                        teamJpaRepo.saveAndFlush(team);
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
                log.info("{} {} vs {} {}", matchOrm.getPandoraId(), matchOrm.getStatus(), dbMatch.getPandoraId(), dbMatch.getStatus());

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
}
