package com.example.gamedetails.models.orm;

import com.example.gamedetails.models.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Match match;


    private int score;
}
