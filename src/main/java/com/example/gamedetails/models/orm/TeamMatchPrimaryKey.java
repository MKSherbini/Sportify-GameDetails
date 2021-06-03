package com.example.gamedetails.models.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TeamMatchPrimaryKey implements Serializable {
    @ManyToOne
    private Team team;
    @ManyToOne
    private Match match;
}
