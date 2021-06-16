package com.example.gamedetails.models.dto;

import com.example.gamedetails.models.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchDto {
    private String name;
    private LocalDateTime scheduledAt;
    private List<TeamMatchScoreDto> teams;
    private MatchStatus status;


}
