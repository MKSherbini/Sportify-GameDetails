package com.example.gamedetails.repos;

import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMemberJpaRepo extends JpaRepository<TeamMember, Integer> {
}
