package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.dto.pandora.PandoraTeamScoreDto;
import com.example.gamedetails.models.enums.MatchStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@Component
public class PandoraMatchAdapter {

    public ArrayList<PandoraMatchesDto> convertToDto(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);

        var pandoraList = new ArrayList<PandoraMatchesDto>();

        for (var element : root) {
            var obj = new PandoraMatchesDto();
            obj.setName(element.get("name").asText());
            obj.setStatus(MatchStatus.valueOf(element.get("status").asText()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            LocalDateTime date = LocalDateTime.parse(element.get("scheduled_at").asText(), formatter);
            obj.setScheduledAt(date);

            var teamScoreList = new ArrayList<PandoraTeamScoreDto>();

//            System.out.println(element.get("opponents"));
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

            obj.setTeams(teamScoreList);

            pandoraList.add(obj);
        }
        return pandoraList;
    }
}
