package com.example.gamedetails.service;

import com.example.gamedetails.adapter.MatchAdapter;
import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.repos.GameJpaRepo;
import com.example.gamedetails.repos.MatchJpaRepo;
import com.example.gamedetails.repos.TeamJpaRepo;
import com.example.gamedetails.repos.TeamMatchScoreJpaRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

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

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void fetchMatches() {
        log.info("FetchingService.fetchMatches");
        fetchMatchesByGame(GamesNames.LOL);
        fetchMatchesByGame(GamesNames.DOTA2);
        fetchMatchesByGame(GamesNames.CODMW);
    }

    private void fetchMatchesByGame(GamesNames gameName) {
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

        for (var item : pandoraMatchesDtos) {
            var matchOrm = matchAdapter.pandoraMatchToTeamMatchOrm(item);
            matchOrm.setGame(gameJpaRepo.getById(gameName));

            var existsByPandoraIdIsAndNameIs = matchJpaRepo.existsByPandoraIdIsAndNameIs(matchOrm.getPandoraId(), matchOrm.getName());
            if (!existsByPandoraIdIsAndNameIs) {
                matchJpaRepo.saveAndFlush(matchOrm);

                item.getPandoraTeamScoreDtos().forEach(pandoraTeamScoreDto -> {
                    Optional<Team> byAcronymAndName = teamJpaRepo.findByAcronymAndName(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName());
                    if (byAcronymAndName.isEmpty()) {
                        var team = new Team(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName());
                        teamJpaRepo.saveAndFlush(team);
                        byAcronymAndName = Optional.of(team);
                    }

                    var teamMatchScore = matchAdapter.pandoraTeamsToOrmTeams(pandoraTeamScoreDto, matchOrm, byAcronymAndName.get());
                    teamMatchScoreJpaRepo.saveAndFlush(teamMatchScore);
                });
            }
        }
    }


    // todo delete old matches
}
