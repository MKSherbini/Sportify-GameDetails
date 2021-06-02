package com.example.gamedetails.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class GameDetails {
    private String name;
    private String description;
    private String platform;
    private Date releaseDate;
    private String profitModel;
    private String genre;
    private String publisher;
    private String designer;
    private int minAge;

//    List<Matches> matches;
//    List<News> news;
}
