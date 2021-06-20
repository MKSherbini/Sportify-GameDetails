package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.TeamDto;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.models.orm.TeamMember;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeamAdapter {
    private final ModelMapper modelMapper;

    public TeamAdapter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeamDto ormToDto(Team teamOrm) {
        return modelMapper.map(teamOrm, TeamDto.class);
    }
}
