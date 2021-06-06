package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMatchPrimaryKey;
import com.example.gamedetails.models.orm.TeamMatchScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMatchScoreJpaRepo extends JpaRepository<TeamMatchScore, TeamMatchPrimaryKey> {
}
