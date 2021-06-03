package com.example.gamedetails.controllers;

import com.example.gamedetails.models.dto.GameDetailsDto;
import com.example.gamedetails.models.dto.MatchesDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameDetailsController {

    private final GameService gameService;

    public GameDetailsController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{game}")
    public GameDetailsDto getGameDetails(@PathVariable GamesNames game) {
        return gameService.getGameDetails(game);
    }

    @GetMapping("/{game}/news")
    public List<NewsDto> getGameNews(@PathVariable GamesNames game) {
        return gameService.getNews(game);
    }

    @GetMapping("/{game}/matches")
    public List<MatchesDto> getGameMatches(@PathVariable GamesNames game) {
        return gameService.getMatches(game);
    }
}
