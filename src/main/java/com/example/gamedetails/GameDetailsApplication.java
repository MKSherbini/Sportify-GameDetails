package com.example.gamedetails;

import com.example.gamedetails.adapter.PandoraMatchAdapter;
import com.example.gamedetails.models.dto.TeamMatchScoreDto;
import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.dto.pandora.PandoraTeamScoreDto;
import com.example.gamedetails.models.enums.MatchStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
@EnableScheduling
public class GameDetailsApplication implements CommandLineRunner {

    @Autowired
    PandoraMatchAdapter pandoraMatchAdapter;

    public static void main(String[] args) {
        SpringApplication.run(GameDetailsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://api.pandascore.co/lol/matches/past?token=D7pVhsHNRdbnETpxVeN3UnSb7oHGQokBzSdECxkm-gov5JIELdE", String.class);
        System.out.println("pandoraList = " + pandoraMatchAdapter.convertToDto(response.getBody()));

//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(response.getBody());

//        element = {"name":"Week 5: IW vs FB",
//        "league":{"id":1003,"image_url":"https://cdn.pandascore.co/images/league/image/1003/turkiye-sampiyonluk-ligi-sffudtf.png",
//        "modified_at":"2019-03-03T15:03:08Z","name":"TCL","slug":"league-of-legends-turkey-championship-league","url":null},
//        "serie_id":3674,"streams":{"english":{"embed_url":null,"raw_url":null},"official":{"embed_url":null,"raw_url":null},
//        "russian":{"embed_url":null,"raw_url":null}},"match_type":"best_of","winner_id":null,"streams_list":[],
//        "serie":{"begin_at":"2021-06-12T11:00:00Z","description":null,"end_at":null,"full_name":"Summer 2021","id":3674,"league_id":1003,
//        "modified_at":"2021-06-04T18:26:23Z","name":null,"season":"Summer","slug":"league-of-legends-turkey-championship-league-summer-2021","tier":"b","winner_id":null,"winner_type":null,"year":2021},"original_scheduled_at":"2021-07-11T15:00:00Z","tournament":{"begin_at":"2021-06-12T11:00:00Z","end_at":"2021-07-11T16:00:00Z","id":6194,"league_id":1003,"live_supported":false,"modified_at":"2021-06-04T18:27:24Z","name":"Regular Season","prizepool":null,"serie_id":3674,"slug":"league-of-legends-turkey-championship-league-summer-2021-regular-season","winner_id":null,"winner_type":"Team"},"live_embed_url":null,"official_stream_url":null,"results":[{"score":0,"team_id":126066},{"score":0,"team_id":330}],"number_of_games":1,"status":"not_started","modified_at":"2021-06-04T18:57:31Z","games":[{"begin_at":null,"complete":false,"detailed_stats":true,"end_at":null,"finished":false,"forfeit":false,"id":225937,"length":null,"match_id":596565,"position":1,"status":"not_started","video_url":null,"winner":{"id":null,"type":"Team"},"winner_type":"Team"}],"scheduled_at":"2021-07-11T15:00:00Z","detailed_stats":true,"forfeit":false,"winner":null,"draw":false,"slug":"istanbul-wildcats-vs-1907-fenerbahce-esports-2021-07-11","videogame_version":null,"videogame":{"id":1,"name":"LoL","slug":"league-of-legends"},"league_id":1003,"id":596565,"rescheduled":false,"begin_at":"2021-07-11T15:00:00Z",
//        "opponents":[{"opponent":{"acronym":"IW","id":126066,"image_url":"https://cdn.pandascore.co/images/team/image/126066/220px_istanbul_wildcatslogo_square.png","location":"TR","modified_at":"2021-05-28T15:10:52Z","name":"Istanbul Wildcats","slug":"istanbul-wildcats"},"type":"Team"},{"opponent":{"acronym":"FB","id":330,"image_url":"https://cdn.pandascore.co/images/team/image/330/1907-fenerbahce-espor-2cum6g3u.png","location":"TR","modified_at":"2021-05-17T17:08:17Z","name":"1907 Fenerbahçe Esports","slug":"1907-fenerbahce-espor"},"type":"Team"}],"tournament_id":6194,"game_advantage":null,"end_at":null,"live":{"opens_at":null,"supported":false,"url":null}}

//        var pandoraList = new ArrayList<PandoraMatchesDto>();
//
//        for (var element : root) {
//            var obj = new PandoraMatchesDto();
//            obj.setName(element.get("name").asText());
//            obj.setStatus(MatchStatus.valueOf(element.get("status").asText()));
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
//            LocalDateTime date = LocalDateTime.parse(element.get("scheduled_at").asText(), formatter);
//            obj.setScheduledAt(date);
//
//            var teamScoreList = new ArrayList<PandoraTeamScoreDto>();
//
////            System.out.println(element.get("opponents"));
//            for (var team : element.get("opponents")) {
//                var teamObj = new PandoraTeamScoreDto();
//                teamObj.setAcronym(team.get("opponent").get("acronym").asText());
//                teamObj.setName(team.get("opponent").get("name").asText());
//                teamScoreList.add(teamObj);
//            }
//
//            var results = element.get("results").elements();
//
//            for (var team : teamScoreList) {
//                team.setScore(results.next().get("score").asInt());
//            }
//
//            obj.setTeams(teamScoreList);
//
//            pandoraList.add(obj);
//            break;
//        }
//        2021-07-11T15:00:00Z


//root.elements().next()
//        var opponents = root.elements().next().get("opponents").elements();
//        System.out.println("root.elements() = " + opponents.next().get("opponent").get("acronym"));
//        System.out.println("root.elements() = " + opponents.next().get("opponent").get("acronym"));
//        System.out.println("root.elements() = " + root.elements().next());
//        System.out.println("root = " + root);

//		JsonNode name = root.path("name");
    }
}
