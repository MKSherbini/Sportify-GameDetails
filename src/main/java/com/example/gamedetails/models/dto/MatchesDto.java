package com.example.gamedetails.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MatchesDto {
    private String title;
    private LocalDateTime matchTime;
    private List<TeamMatchScoreDto> teams;
    private boolean inProgress;


}
