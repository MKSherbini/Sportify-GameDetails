package com.example.gamedetails.service;

import com.example.gamedetails.adapter.PlayerAdapter;
import com.example.gamedetails.models.dto.PlayerDto;
import com.example.gamedetails.models.dto.TeamDto;
import com.example.gamedetails.models.orm.Team;
import com.example.gamedetails.repos.PlayerJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerJpaRepo playerJpaRepo;
    private final PlayerAdapter playerAdapter;

    public PlayerService(PlayerJpaRepo playerJpaRepo, PlayerAdapter playerAdapter) {
        this.playerJpaRepo = playerJpaRepo;
        this.playerAdapter = playerAdapter;
    }


    public List<PlayerDto> getAllPlayers() {
        return playerJpaRepo.findAll().stream()
                .map(playerAdapter::ormToDto).collect(Collectors.toList());
    }

    public PlayerDto getPlayer(int id) {
        var obj = playerJpaRepo.findById(id);
        if (obj.isEmpty()) return null;
        return playerAdapter.ormToDto(obj.get());
    }
}
