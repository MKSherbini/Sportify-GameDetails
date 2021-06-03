package com.example.gamedetails.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamMatchScoreDto {
    private TeamDto team;
    private int score;
}
