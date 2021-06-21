package com.example.gamedetails.service;

import com.example.gamedetails.adapter.*;
import com.example.gamedetails.models.dto.*;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Game;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.repos.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameJpaRepo gameJpaRepo;
    private final NewsAdapter newsAdapter;
    private final MatchAdapter matchAdapter;
    private final GameAdapter gameAdapter;

    public GameService(GameJpaRepo gameJpaRepo, NewsAdapter newsAdapter, MatchAdapter matchAdapter, GameAdapter gameAdapter) {
        this.gameJpaRepo = gameJpaRepo;
        this.newsAdapter = newsAdapter;
        this.matchAdapter = matchAdapter;
        this.gameAdapter = gameAdapter;
    }

    public List<NewsDto> getNews(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return new ArrayList<>();

        return gameObj.get().getNews().stream()
                .map(newsAdapter::ormToDto).collect(Collectors.toList());
    }

    public List<MatchDto> getMatches(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return new ArrayList<>();

        return gameObj.get().getMatches().stream()
                .map(matchAdapter::ormToDto).collect(Collectors.toList());
    }

    public List<GameDto> getGameDetails() {
        return gameJpaRepo.findAll().stream()
                .map(gameAdapter::ormToDto)
                .collect(Collectors.toList());
    }

    public GameDto getGameDetails(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return null;

        return gameAdapter.ormToDto(gameObj.get());
    }
}
