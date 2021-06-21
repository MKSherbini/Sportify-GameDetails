package com.example.gamedetails.controllers;

import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@CrossOrigin
public class MatchesController {

    private final MatchService matchService;

    public MatchesController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{matchId}")
    public MatchDto getMatch(@PathVariable Integer matchId) {
        return matchService.getMatch(matchId);
    }

    @GetMapping("/")
    public List<MatchDto> getAllMatches(){
        return matchService.getMatches();
    }
}
