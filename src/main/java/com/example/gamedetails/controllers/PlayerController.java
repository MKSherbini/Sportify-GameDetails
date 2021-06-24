package com.example.gamedetails.controllers;

import com.example.gamedetails.models.dto.PlayerDto;
import com.example.gamedetails.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@CrossOrigin
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{playerId}")
    public PlayerDto getPlayer(@PathVariable Integer playerId) {
        return playerService.getPlayer(playerId);
    }

    @GetMapping
    public List<PlayerDto> getPlayers(){
        return playerService.getAllPlayers();
    }
}
