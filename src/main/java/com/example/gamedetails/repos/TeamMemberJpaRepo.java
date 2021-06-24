package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TeamMemberJpaRepo extends JpaRepository<TeamMember, Integer> {
}
