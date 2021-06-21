package com.example.gamedetails.models.dto;

import com.example.gamedetails.models.enums.GamesNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDetailsDto {
    private Integer id;
    private String title;
    private String image;
    private String description;
    private Date date;
    private String publisher;
    //hanb2a ntfahem
    private String content;

    private GamesNames game;
}
