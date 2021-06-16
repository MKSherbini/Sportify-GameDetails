package com.example.gamedetails.repos;

import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// todo expose full crud to admin only
@RepositoryRestResource(path = "gamesDb", collectionResourceRel = "gamesDb")
public interface GameJpaRepo extends JpaRepository<Game, GamesNames> {
}
