package com.example.gamedetails.models.orm;

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
@Table(name = "team_members")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String birthYear;
    private String birthday;
    private String hometown;
    @Column(length = 99999)
    private String imageUrl;
    private String firstName;
    private String lastName;
    private String name;
    private String nationality;

    public TeamMember(String birthYear, String birthday, String hometown, String imageUrl, String firstName, String lastName, String name, String nationality) {
        this.birthYear = birthYear;
        this.birthday = birthday;
        this.hometown = hometown;
        this.imageUrl = imageUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.nationality = nationality;
    }

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    private Team team;
}
