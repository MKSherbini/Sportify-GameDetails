package com.example.gamedetails.models.dto.pandora;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PandoraTeamScoreDto {
    private int id;
    private String acronym;
    private String name;
    private int score;
    private String imageUrl;
}
