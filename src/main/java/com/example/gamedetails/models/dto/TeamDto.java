package com.example.gamedetails.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private long id;
    private String acronym;
    private String name;
    private String imageUrl;
    private List<TeamMemberDto> teamMembers = new ArrayList<>();
}
