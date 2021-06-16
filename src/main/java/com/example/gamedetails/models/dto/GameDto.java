package com.example.gamedetails.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private String codeName;
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
