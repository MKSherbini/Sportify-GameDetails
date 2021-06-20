package com.example.gamedetails.service;

import com.example.gamedetails.adapter.*;
import com.example.gamedetails.models.dto.GameDto;
import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.dto.TeamDto;
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
    private final MatchJpaRepo matchJpaRepo;
    private final NewsJpaRepo newsJpaRepo;
    private final NewsAdapter newsAdapter;
    private final MatchAdapter matchAdapter;
    private final GameAdapter gameAdapter;
    private final TeamJpaRepo teamJpaRepo;
    private final TeamAdapter teamAdapter;

    public GameService(GameJpaRepo gameJpaRepo, MatchJpaRepo matchJpaRepo, NewsJpaRepo newsJpaRepo, NewsAdapter newsAdapter, MatchAdapter matchAdapter, GameAdapter gameAdapter, TeamJpaRepo teamJpaRepo, TeamMemberJpaRepo teamMemberJpaRepo, TeamMemberAdapter teamMemberAdapter, TeamAdapter teamAdapter) {
        this.gameJpaRepo = gameJpaRepo;
        this.matchJpaRepo = matchJpaRepo;
        this.newsJpaRepo = newsJpaRepo;
        this.newsAdapter = newsAdapter;
        this.matchAdapter = matchAdapter;
        this.gameAdapter = gameAdapter;
        this.teamJpaRepo = teamJpaRepo;
        this.teamAdapter = teamAdapter;
    }

    public List<NewsDto> getNews() {
        return newsJpaRepo.findAll().stream()
                .map(newsAdapter::ormToDto).collect(Collectors.toList());
    }

    public NewsDto getNews(Integer id) {
        return newsAdapter.ormToDto(newsJpaRepo.getById(id));
    }

    public List<NewsDto> getNews(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return new ArrayList<>();

        return gameObj.get().getNews().stream()
                .map(newsAdapter::ormToDto).collect(Collectors.toList());
    }

    public List<MatchDto> getMatches() {
        return matchJpaRepo.findAll().stream()
                .map(matchAdapter::ormToDto).collect(Collectors.toList());
    }

    public MatchDto getMatch(Integer id) {
        return matchAdapter.ormToDto(matchJpaRepo.getById(id));
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

    public TeamDto getTeam(int id) {
        Optional<Team> obj = teamJpaRepo.findById(id);
        if (obj.isEmpty()) return null;

        return teamAdapter.ormToDto(obj.get());
    }
}
