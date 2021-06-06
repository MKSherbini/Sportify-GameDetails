package com.example.gamedetails.models.orm;

import com.example.gamedetails.models.dto.TeamDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teams_matches")
@IdClass(TeamMatchPrimaryKey.class)
public class TeamMatchScore {
    @Id
    @ManyToOne
    private Team team;
    @Id
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Match match;


    private int score;
}
