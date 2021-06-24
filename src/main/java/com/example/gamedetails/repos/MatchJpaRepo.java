package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Game;
import com.example.gamedetails.models.orm.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MatchJpaRepo extends JpaRepository<Match, Integer> {
    boolean existsByPandoraIdIsAndNameIs(Integer pandoraId, String name);

    // todo eb2a esma3 kalam hadeer :)
    Match queryByPandoraIdIsAndNameIs(Integer pandoraId, String name);

}
