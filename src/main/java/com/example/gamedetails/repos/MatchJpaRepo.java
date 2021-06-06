package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Game;
import com.example.gamedetails.models.orm.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchJpaRepo extends JpaRepository<Match, Integer> {
    boolean existsByPandoraIdIsAndNameIs(Integer pandoraId, String name);
}
