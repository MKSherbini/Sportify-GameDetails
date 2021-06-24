package com.example.gamedetails.models.dto;

import com.example.gamedetails.models.orm.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private Integer id;
    private String birthYear;
    private String birthday;
    private String hometown;
    private String imageUrl;
    private String firstName;
    private String lastName;
    private String name;
    private String nationality;

    private String teamAcronym;
    private String teamName;
    private String teamImageUrl;
}
