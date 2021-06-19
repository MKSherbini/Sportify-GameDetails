package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.GameDto;
import com.example.gamedetails.models.orm.Game;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GameAdapter {
    private final ModelMapper modelMapper;

    public GameAdapter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GameDto ormToDto(Game gameOrm) {
        return modelMapper.map(gameOrm, GameDto.class);
    }
}
