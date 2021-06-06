package com.example.gamedetails;

import com.example.gamedetails.adapter.MatchAdapter;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Game;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMatchScore;
import com.example.gamedetails.repos.GameJpaRepo;
import com.example.gamedetails.repos.MatchJpaRepo;
import com.example.gamedetails.repos.TeamJpaRepo;
import com.example.gamedetails.repos.TeamMatchScoreJpaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

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
//    @Order(3)
    CommandLineRunner addGames() {
        return args -> {
            // todo update these
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

            if (!gameJpaRepo.existsById(GamesNames.DOTA2)) {
                var dota2 = new Game();
                dota2.setCodeName(GamesNames.DOTA2);
                dota2.setName("DotA 2");
                dota2.setDescription("League of Legends (LoL) is a team-based online multiplayer by Riot Games. It is one of the most successful video games of all time and has a strong presence in the esports industry. LoL is a Free2Play MOBA, a Multiplayer Online Battle Arena game, and has around 8 Million concurrent players (2019). Two teams play 5 v 5 on the map Summoner’s Rift, with the objective of destroying each other’s base-core, the Nexus. Each match starts with the selection of a character, a Champion. There are over 150 different Champions. They differ in looks, abilities, attack type and playstyle. Bases are separated by three different lanes. The area between those lanes is called the Jungle. In order to get to the enemy base, you have to destroy towers, that are placed on the all three lanes. Minions, that are periodically spawned from your nexus, help you with your objective and automatically attack towers on their way to the enemy base. Inside the Jungle there are two main objectives that provide powerful buffs, after being slain by a team: The Baron Nashor and the elemental Drakes. In order to win you have to gain experience and gold, by defeating enemy players and monsters. A game ends, when one of the Nexus explode.");
                dota2.setPlatform("Windows, Mac");
                dota2.setReleaseDate(new Date((1256673833000L)));
                dota2.setProfitModel("Free2Play + Microtransactions");
                dota2.setGenre("Multiplayer Online Battle Arena, (MOBA)");
                dota2.setPublisher("Riot Games Inc., Tencent Holdings Ltd. (owner of branch in Los-Angeles)");
                dota2.setDesigner("Riot Games");
                dota2.setMinAge(12);
                gameJpaRepo.saveAndFlush(dota2);
            }

            if (!gameJpaRepo.existsById(GamesNames.CODMW)) {
                var cod = new Game();
                cod.setCodeName(GamesNames.CODMW);
                cod.setName("Call Of Duty");
                cod.setDescription("League of Legends (LoL) is a team-based online multiplayer by Riot Games. It is one of the most successful video games of all time and has a strong presence in the esports industry. LoL is a Free2Play MOBA, a Multiplayer Online Battle Arena game, and has around 8 Million concurrent players (2019). Two teams play 5 v 5 on the map Summoner’s Rift, with the objective of destroying each other’s base-core, the Nexus. Each match starts with the selection of a character, a Champion. There are over 150 different Champions. They differ in looks, abilities, attack type and playstyle. Bases are separated by three different lanes. The area between those lanes is called the Jungle. In order to get to the enemy base, you have to destroy towers, that are placed on the all three lanes. Minions, that are periodically spawned from your nexus, help you with your objective and automatically attack towers on their way to the enemy base. Inside the Jungle there are two main objectives that provide powerful buffs, after being slain by a team: The Baron Nashor and the elemental Drakes. In order to win you have to gain experience and gold, by defeating enemy players and monsters. A game ends, when one of the Nexus explode.");
                cod.setPlatform("Windows, Mac");
                cod.setReleaseDate(new Date((1256673833000L)));
                cod.setProfitModel("Free2Play + Microtransactions");
                cod.setGenre("Multiplayer Online Battle Arena, (MOBA)");
                cod.setPublisher("Riot Games Inc., Tencent Holdings Ltd. (owner of branch in Los-Angeles)");
                cod.setDesigner("Riot Games");
                cod.setMinAge(12);
                gameJpaRepo.saveAndFlush(cod);
            }

        };
    }
}
