package com.example.gamedetails.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TeamDto {
    private long id;
    private String acronym;
    private String name;
}
