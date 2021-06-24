package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMatchPrimaryKey;
import com.example.gamedetails.models.orm.TeamMatchScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TeamMatchScoreJpaRepo extends JpaRepository<TeamMatchScore, TeamMatchPrimaryKey> {
}
