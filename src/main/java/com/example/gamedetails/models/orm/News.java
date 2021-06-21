package com.example.gamedetails.models.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 999999)
    private String title;
    @Column(length = 99999)
    private String image;
    @Column(length = 999999)
    private String description;
    private Date date;
    private String publisher;
    //hanb2a ntfahem
    @Column(length = 999999)
    private String content;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
