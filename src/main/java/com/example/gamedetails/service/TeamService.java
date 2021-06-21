package com.example.gamedetails.service;

import com.example.gamedetails.adapter.TeamAdapter;
import com.example.gamedetails.models.dto.TeamDto;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.repos.TeamJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    TeamJpaRepo teamJpaRepo;
    TeamAdapter teamAdapter;

    public TeamService(TeamJpaRepo teamJpaRepo, TeamAdapter teamAdapter) {
        this.teamJpaRepo = teamJpaRepo;
        this.teamAdapter = teamAdapter;
    }

    public List<TeamDto> getAllTeams(){
        return teamJpaRepo.findAll().stream()
                .map(teamAdapter::ormToDto).collect(Collectors.toList());
    }

    public TeamDto getTeam(int id){
        Optional<Team> obj = teamJpaRepo.findById(id);
        if (obj.isEmpty()) return null;
        return teamAdapter.ormToDto(obj.get());
    }
}
