package com.example.gamedetails.models.orm;

import com.example.gamedetails.models.dto.TeamDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teams_matches")
@IdClass(TeamMatchPrimaryKey.class)
public class TeamMatchScore implements Persistable<TeamMatchPrimaryKey> {
    @Id
    @ManyToOne
    private Team team;
    @Id
    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Match match;


    private int score;


    @Transient
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public TeamMatchPrimaryKey getId() {
        return new TeamMatchPrimaryKey(getTeam(), getMatch());
    }
}
