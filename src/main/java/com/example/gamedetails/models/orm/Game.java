package com.example.gamedetails.models.orm;

import com.example.gamedetails.models.enums.GamesNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {
    @Id
    private GamesNames codeName;

    private String name;
    private String description;
    private String platform;
    private Date releaseDate;
    private String profitModel;
    private String genre;
    private String publisher;
    private String designer;
    private int minAge;

    @OneToMany(mappedBy = "game")
    List<Match> matches = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    List<News> news = new ArrayList<>();
}
