package com.example.gamedetails;

import com.example.gamedetails.adapter.MatchAdapter;
import com.example.gamedetails.models.dto.pandora.PandoraMatchesDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Game;
import com.example.gamedetails.models.orm.Match;
import com.example.gamedetails.models.orm.TeamMatchScore;
import com.example.gamedetails.repos.GameJpaRepo;
import com.example.gamedetails.repos.MatchJpaRepo;
import com.example.gamedetails.repos.TeamJpaRepo;
import com.example.gamedetails.repos.TeamMatchScoreJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class GameDetailsApplication {

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
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(GameDetailsApplication.class, args);
    }

    @Bean
//    @Order(2)
    CommandLineRunner addLolMatches() {
        return args -> {
            addGames().run(args);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("https://api.pandascore.co/lol/matches/past?token=D7pVhsHNRdbnETpxVeN3UnSb7oHGQokBzSdECxkm-gov5JIELdE", String.class);


            var pandoraMatchesDtos = matchAdapter.jsonToPandoraMatchesDto(response.getBody());

            for (var item : pandoraMatchesDtos) {
                var matchOrm = matchAdapter.pandoraMatchToTeamMatchOrm(item);
                matchOrm.setGame(gameJpaRepo.getById(GamesNames.LOL));

                var existsByPandoraIdIsAndNameIs = matchJpaRepo.existsByPandoraIdIsAndNameIs(matchOrm.getPandoraId(), matchOrm.getName());
                if (!existsByPandoraIdIsAndNameIs) {
                    matchJpaRepo.save(matchOrm);
                }

//            for (var team : orm.getTeams()) {
//                teamJpaRepo.save(team.getTeam());
//                var teamMatchScore = new TeamMatchScore();
//                teamMatchScore.setTeam(team.getTeam());
//                teamMatchScore.setMatch(orm);
//                teamMatchScore.setScore(team.getScore());
//                teamMatchScoreJpaRepo.save(teamMatchScore);
//            }
            }

            matchJpaRepo.flush();

//        element = {"name":"Week 5: IW vs FB",
//        "league":{"id":1003,"image_url":"https://cdn.pandascore.co/images/league/image/1003/turkiye-sampiyonluk-ligi-sffudtf.png",
//        "modified_at":"2019-03-03T15:03:08Z","name":"TCL","slug":"league-of-legends-turkey-championship-league","url":null},
//        "serie_id":3674,"streams":{"english":{"embed_url":null,"raw_url":null},"official":{"embed_url":null,"raw_url":null},
//        "russian":{"embed_url":null,"raw_url":null}},"match_type":"best_of","winner_id":null,"streams_list":[],
//        "serie":{"begin_at":"2021-06-12T11:00:00Z","description":null,"end_at":null,"full_name":"Summer 2021","id":3674,"league_id":1003,
//        "modified_at":"2021-06-04T18:26:23Z","name":null,"season":"Summer","slug":"league-of-legends-turkey-championship-league-summer-2021","tier":"b","winner_id":null,"winner_type":null,"year":2021},"original_scheduled_at":"2021-07-11T15:00:00Z","tournament":{"begin_at":"2021-06-12T11:00:00Z","end_at":"2021-07-11T16:00:00Z","id":6194,"league_id":1003,"live_supported":false,"modified_at":"2021-06-04T18:27:24Z","name":"Regular Season","prizepool":null,"serie_id":3674,"slug":"league-of-legends-turkey-championship-league-summer-2021-regular-season","winner_id":null,"winner_type":"Team"},"live_embed_url":null,"official_stream_url":null,"results":[{"score":0,"team_id":126066},{"score":0,"team_id":330}],"number_of_games":1,"status":"not_started","modified_at":"2021-06-04T18:57:31Z","games":[{"begin_at":null,"complete":false,"detailed_stats":true,"end_at":null,"finished":false,"forfeit":false,"id":225937,"length":null,"match_id":596565,"position":1,"status":"not_started","video_url":null,"winner":{"id":null,"type":"Team"},"winner_type":"Team"}],"scheduled_at":"2021-07-11T15:00:00Z","detailed_stats":true,"forfeit":false,"winner":null,"draw":false,"slug":"istanbul-wildcats-vs-1907-fenerbahce-esports-2021-07-11","videogame_version":null,"videogame":{"id":1,"name":"LoL","slug":"league-of-legends"},"league_id":1003,"id":596565,"rescheduled":false,"begin_at":"2021-07-11T15:00:00Z",
//        "opponents":[{"opponent":{"acronym":"IW","id":126066,"image_url":"https://cdn.pandascore.co/images/team/image/126066/220px_istanbul_wildcatslogo_square.png","location":"TR","modified_at":"2021-05-28T15:10:52Z","name":"Istanbul Wildcats","slug":"istanbul-wildcats"},"type":"Team"},{"opponent":{"acronym":"FB","id":330,"image_url":"https://cdn.pandascore.co/images/team/image/330/1907-fenerbahce-espor-2cum6g3u.png","location":"TR","modified_at":"2021-05-17T17:08:17Z","name":"1907 Fenerbahçe Esports","slug":"1907-fenerbahce-espor"},"type":"Team"}],"tournament_id":6194,"game_advantage":null,"end_at":null,"live":{"opens_at":null,"supported":false,"url":null}}

//        2021-07-11T15:00:00Z
            ((ConfigurableApplicationContext) applicationContext).close();
        };
    }

//    @Bean
//    @Order(3)
    CommandLineRunner addGames() {
        return args -> {
            if (!gameJpaRepo.existsById(GamesNames.LOL)) {
                var lol = new Game();
                lol.setCodeName(GamesNames.LOL);
                lol.setName("League of Legends");
                lol.setDescription("League of Legends (LoL) is a team-based online multiplayer by Riot Games. It is one of the most successful video games of all time and has a strong presence in the esports industry. LoL is a Free2Play MOBA, a Multiplayer Online Battle Arena game, and has around 8 Million concurrent players (2019). Two teams play 5 v 5 on the map Summoner’s Rift, with the objective of destroying each other’s base-core, the Nexus. Each match starts with the selection of a character, a Champion. There are over 150 different Champions. They differ in looks, abilities, attack type and playstyle. Bases are separated by three different lanes. The area between those lanes is called the Jungle. In order to get to the enemy base, you have to destroy towers, that are placed on the all three lanes. Minions, that are periodically spawned from your nexus, help you with your objective and automatically attack towers on their way to the enemy base. Inside the Jungle there are two main objectives that provide powerful buffs, after being slain by a team: The Baron Nashor and the elemental Drakes. In order to win you have to gain experience and gold, by defeating enemy players and monsters. A game ends, when one of the Nexus explode.");
                lol.setPlatform("Windows, Mac");
                lol.setReleaseDate(new Date((1256673833000L)));
                lol.setProfitModel("Free2Play + Microtransactions");
                lol.setGenre("Multiplayer Online Battle Arena, (MOBA)");
                lol.setPublisher("Riot Games Inc., Tencent Holdings Ltd. (owner of branch in Los-Angeles)");
                lol.setDesigner("Riot Games");
                lol.setMinAge(12);
                gameJpaRepo.saveAndFlush(lol);
            }
        };
    }
}
