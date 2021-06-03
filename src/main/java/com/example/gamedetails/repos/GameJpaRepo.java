package com.example.gamedetails.repos;

import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Game;
import org.springframework.data.jpa.repository.JpaRepository;

// todo expose full crud to admin only
public interface GameJpaRepo extends JpaRepository<Game, GamesNames> {
}
