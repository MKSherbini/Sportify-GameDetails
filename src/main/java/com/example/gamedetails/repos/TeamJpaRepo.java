package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.News;
import com.example.gamedetails.models.orm.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamJpaRepo extends JpaRepository<Team, Integer> {
    Optional<Team> findByAcronymAndName(String acronym, String name);
}
