package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PlayerJpaRepo extends JpaRepository<Player, Integer> {
}
