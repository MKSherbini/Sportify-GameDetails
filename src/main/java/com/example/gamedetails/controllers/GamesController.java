package com.example.gamedetails.controllers;

import com.example.gamedetails.models.dto.GameDto;
import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin
public class GamesController {

    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{game}")
    public GameDto getGameDetails(@PathVariable GamesNames game) {
        return gameService.getGameDetails(game);
    }

    @GetMapping("/{game}/news")
    public List<NewsDto> getGameNews(@PathVariable GamesNames game) {
        return gameService.getNews(game);
    }

    @GetMapping("/{game}/matches")
    public List<MatchDto> getGameMatches(@PathVariable GamesNames game) {
        return gameService.getMatches(game);
    }
}
