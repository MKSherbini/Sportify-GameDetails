package com.example.gamedetails.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Matches {
    private String title;
    private LocalDateTime matchTime;
    private List<TeamMatchScore> teams;
    private boolean inProgress;


}
