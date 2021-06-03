package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.News;
import com.example.gamedetails.models.orm.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepo extends JpaRepository<Team, Integer> {
}
