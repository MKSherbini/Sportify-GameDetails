package com.example.gamedetails.controllers;

import com.example.gamedetails.adapter.TeamAdapter;
import com.example.gamedetails.models.dto.TeamDto;
import com.example.gamedetails.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@CrossOrigin
public class TeamsController {
    TeamService teamService;
    TeamAdapter teamAdapter;

    public TeamsController(TeamService teamService, TeamAdapter teamAdapter) {
        this.teamService = teamService;
        this.teamAdapter = teamAdapter;
    }

    @GetMapping("/{teamId}")
    public TeamDto getTeam(@PathVariable Integer teamId) {
        return teamService.getTeam(teamId);
    }

    @GetMapping
    public List<TeamDto> getTeams(){
        return teamService.getAllTeams();
    }
}
