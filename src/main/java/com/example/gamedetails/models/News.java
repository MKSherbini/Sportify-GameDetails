package com.example.gamedetails.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class News {
    private String title;
    private String image;
    private String description;
    private Date date;
    private String publisher;
    //hanb2a ntfahem
    private String content;
}
