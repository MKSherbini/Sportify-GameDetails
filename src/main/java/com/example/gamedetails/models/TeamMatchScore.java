package com.example.gamedetails.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamMatchScore {
    private Team team;
    private int score;
}
