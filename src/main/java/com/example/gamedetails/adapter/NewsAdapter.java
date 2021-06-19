package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.MatchDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.orm.Match;
import com.example.gamedetails.models.orm.News;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsAdapter {
    private final ModelMapper modelMapper;

    public NewsAdapter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public NewsDto ormToDto(News newsOrm) {
        var newsDto = modelMapper.map(newsOrm, NewsDto.class);
        newsDto.setGame(newsOrm.getGame().getCodeName().toString());
        return newsDto;
    }
}
