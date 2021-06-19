package com.example.gamedetails.service;

import com.example.gamedetails.models.dto.GameDto;
import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.Game;
import com.example.gamedetails.repos.GameJpaRepo;
import com.example.gamedetails.repos.MatchJpaRepo;
import com.example.gamedetails.repos.NewsJpaRepo;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public GameService(GameJpaRepo gameJpaRepo, MatchJpaRepo matchJpaRepo, NewsJpaRepo newsJpaRepo, ModelMapper modelMapper) {
        this.gameJpaRepo = gameJpaRepo;
        this.matchJpaRepo = matchJpaRepo;
        this.newsJpaRepo = newsJpaRepo;
        this.modelMapper = modelMapper;
    }

    public List<NewsDto> getNews() {
        return newsJpaRepo.findAll().stream()
                .map(news -> {
                    NewsDto newsDto = modelMapper.map(news, NewsDto.class);
                    newsDto.setGame(news.getGame().getCodeName().toString());
                    return newsDto;
                }).collect(Collectors.toList());
    }

    public List<NewsDto> getNews(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return new ArrayList<>();

        return gameObj.get().getNews().stream()
                .map(news -> {
                    NewsDto newsDto = modelMapper.map(news, NewsDto.class);
                    newsDto.setGame(news.getGame().getCodeName().toString());
                    return newsDto;
                }).collect(Collectors.toList());
    }

    public List<MatchDto> getMatches() {
        return matchJpaRepo.findAll().stream()
                .map(match -> {
                    var matchDto = modelMapper.map(match, MatchDto.class);
                    matchDto.setGame(match.getGame().getCodeName().toString());
                    return matchDto;
                }).collect(Collectors.toList());
    }

    public MatchDto getMatch(Integer id) {
        return modelMapper.map(matchJpaRepo.getById(id), MatchDto.class);
    }

    public NewsDto getNews(Integer id) {
        return modelMapper.map(newsJpaRepo.getById(id), NewsDto.class);
    }

    public List<MatchDto> getMatches(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return new ArrayList<>();

        return gameObj.get().getMatches().stream()
                .map(match -> {
                    var matchDto = modelMapper.map(match, MatchDto.class);
                    matchDto.setGame(match.getGame().getCodeName().toString());
                    return matchDto;
                }).collect(Collectors.toList());
    }

    public List<GameDto> getGameDetails() {
        return gameJpaRepo.findAll().stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());
    }

    public GameDto getGameDetails(GamesNames game) {
        Optional<Game> gameObj = gameJpaRepo.findById(game);
        if (gameObj.isEmpty()) return null;

        return modelMapper.map(gameObj.get(), GameDto.class);
    }
}
