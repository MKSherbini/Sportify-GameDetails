package com.example.gamedetails.controllers;

import com.example.gamedetails.models.dto.GameDto;
import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.repos.MatchJpaRepo;
import com.example.gamedetails.repos.NewsJpaRepo;
import com.example.gamedetails.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin
public class GamesController {

    private final GameService gameService;


    public GamesController(GameService gameService, MatchJpaRepo matchJpaRepo, NewsJpaRepo newsJpaRepo) {
        this.gameService = gameService;
    }

    @GetMapping()
    public List<GameDto> getDetails() {
        return gameService.getGameDetails();
    }

    @GetMapping("/{game}")
    public GameDto getGameDetails(@PathVariable GamesNames game) {
        return gameService.getGameDetails(game);
    }

    @GetMapping("/news")
    public List<NewsDto> getNews() {
        return gameService.getNews();
    }

    @GetMapping("/news/{newsId}")
    public NewsDto getNews(@PathVariable Integer newsId) {
        return gameService.getNews(newsId);
    }

    @GetMapping("/{game}/news")
    public List<NewsDto> getGameNews(@PathVariable GamesNames game) {
        return gameService.getNews(game);
    }

    @GetMapping("/matches")
    public List<MatchDto> getMatches() {
        return gameService.getMatches();
    }

    @GetMapping("/matches/{matchId}")
    public MatchDto getMatch(@PathVariable Integer matchId) {
        return gameService.getMatch(matchId);
    }

    @GetMapping("/{game}/matches")
    public List<MatchDto> getGameMatches(@PathVariable GamesNames game) {
        return gameService.getMatches(game);
    }

}
