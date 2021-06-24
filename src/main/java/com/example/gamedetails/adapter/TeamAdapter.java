package com.example.gamedetails.adapter;

import com.example.gamedetails.models.dto.TeamDto;
import com.example.gamedetails.models.orm.Team;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class TeamAdapter {
    private final ModelMapper modelMapper;
    private final PlayerAdapter playerAdapter;


    public TeamAdapter(ModelMapper modelMapper, PlayerAdapter playerAdapter) {
        this.modelMapper = modelMapper;
        this.playerAdapter = playerAdapter;
    }

    public TeamDto ormToDto(Team teamOrm) {
        var teamDto = modelMapper.map(teamOrm, TeamDto.class);
        teamDto.setPlayers(teamOrm.getPlayers().stream().map(playerAdapter::ormToDto).collect(Collectors.toList()));
        for (var player :
                teamOrm.getPlayers()) {
            log.info(player.toString());
        }
        log.info(teamOrm.getPlayers().toString());
        log.info(teamOrm.toString());
        return teamDto;
    }
}
