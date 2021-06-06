package com.example.gamedetails.models.dto.pandora;

import com.example.gamedetails.models.enums.MatchStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PandoraMatchesDto {
    private int id;
    private String name;
    private MatchStatus status;
    private LocalDateTime scheduledAt;

    private List<PandoraTeamScoreDto> pandoraTeamScoreDtos;
}
