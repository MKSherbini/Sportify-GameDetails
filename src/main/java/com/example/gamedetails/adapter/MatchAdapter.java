package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.dto.pandora.PandoraTeamScoreDto;
import com.example.gamedetails.models.enums.MatchStatus;
import com.example.gamedetails.models.orm.Match;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMatchScore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class MatchAdapter {


    public List<PandoraMatchesDto> jsonToPandoraMatchesDto(String body) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);

        var pandoraList = new ArrayList<PandoraMatchesDto>();

        for (var element : root) {
            var obj = new PandoraMatchesDto();
            obj.setName(element.get("name").asText());
            obj.setId(element.get("id").asInt());
            obj.setStatus(MatchStatus.valueOf(element.get("status").asText().toUpperCase()));

            try {
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
                var date = LocalDateTime.parse(element.get("scheduled_at").asText(), formatter);
                obj.setScheduledAt(date);
            } catch (java.time.format.DateTimeParseException e) {
                continue;
            }

            var teamScoreList = new ArrayList<PandoraTeamScoreDto>();

            for (var team : element.get("opponents")) {
                var teamObj = new PandoraTeamScoreDto();
                teamObj.setAcronym(team.get("opponent").get("acronym").asText());
                teamObj.setName(team.get("opponent").get("name").asText());
                teamScoreList.add(teamObj);
            }

            var results = element.get("results").elements();

            for (var team : teamScoreList) {
                team.setScore(results.next().get("score").asInt());
            }

            obj.setPandoraTeamScoreDtos(teamScoreList);

            pandoraList.add(obj);
        }
        return pandoraList;
    }

    public Match pandoraMatchToTeamMatchOrm(PandoraMatchesDto pandoraMatchesDto) {
        var matchOrm = new Match();
        matchOrm.setPandoraId(pandoraMatchesDto.getId());
        matchOrm.setName(pandoraMatchesDto.getName());
        matchOrm.setStatus(pandoraMatchesDto.getStatus());
        matchOrm.setScheduledAt(pandoraMatchesDto.getScheduledAt());

//        var teamMatchScores = new ArrayList<TeamMatchScore>();
//
//        for (var pandoraTeamScoreDto : pandoraMatchesDto.getTeams()) {
//            teamMatchScores.add(pandoraTeamsToOrmTeams(pandoraTeamScoreDto, matchOrm));
//        }
//
//        matchOrm.setTeams(teamMatchScores);
        return matchOrm;
    }

    public TeamMatchScore pandoraTeamsToOrmTeams(PandoraTeamScoreDto pandoraTeamScoreDto, Match matchOrm, Team team) {
        var teamMatchScore = new TeamMatchScore();
        teamMatchScore.setMatch(matchOrm);
        teamMatchScore.setScore(pandoraTeamScoreDto.getScore());
        teamMatchScore.setTeam(team);
//        new Team(pandoraTeamScoreDto.getAcronym(), pandoraTeamScoreDto.getName())
        return teamMatchScore;
    }

}
