package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.NewsDetailsDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.enums.GamesNames;
import com.example.gamedetails.models.orm.News;
import com.example.gamedetails.repos.GameJpaRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NewsAdapter {
    private final ModelMapper modelMapper;
    private final GameJpaRepo gameJpaRepo;

    public NewsAdapter(ModelMapper modelMapper, GameJpaRepo gameJpaRepo) {
        this.modelMapper = modelMapper;
        this.gameJpaRepo = gameJpaRepo;
    }

    public NewsDto ormToDto(News newsOrm) {
        var newsDto = modelMapper.map(newsOrm, NewsDto.class);
        newsDto.setGame(newsOrm.getGame().getCodeName().toString());
        return newsDto;
    }

    public NewsDetailsDto ormToNewsDetailsDto(News newsOrm) {
        var newsDetailsDto = modelMapper.map(newsOrm, NewsDetailsDto.class);
        newsDetailsDto.setGame(newsOrm.getGame().getCodeName());
        return newsDetailsDto;
    }

    public News dtoToOrm(NewsDto newsDto) {
        var newsOrm = modelMapper.map(newsDto, News.class);
        newsOrm.setId(null);
        log.info(String.valueOf(gameJpaRepo.getById(GamesNames.valueOf(newsDto.getGame().toUpperCase()))));
        newsOrm.setGame(gameJpaRepo.getById(GamesNames.valueOf(newsDto.getGame().toUpperCase())));
        return newsOrm;
    }
}
