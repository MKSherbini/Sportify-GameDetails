package com.example.gamedetails.models.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String acronym;
    private String name;
    @Column(length = 99999)
    private String imageUrl;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @ToString.Exclude
    private List<TeamMatchScore> teamMatchScores = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Team(String acronym, String name, String imageUrl) {
        this.acronym = acronym;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
