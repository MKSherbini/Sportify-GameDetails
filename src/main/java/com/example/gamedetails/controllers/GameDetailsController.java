package com.example.gamedetails.controllers;

import com.example.gamedetails.factory.GameAdapterFactory;
import com.example.gamedetails.models.GameDetails;
import com.example.gamedetails.models.GamesNames;
import com.example.gamedetails.models.Matches;
import com.example.gamedetails.models.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameDetailsController {

    @Autowired
    GameAdapterFactory gameAdapterFactory;

    @GetMapping("/{game}/news")
    public List<News> getGameNews(@PathVariable GamesNames game){

        return gameAdapterFactory.getGameAdapter(game).getNews();
    }

    @GetMapping("/{game}/matches")
    public List<Matches> getGameMatches(@PathVariable GamesNames game){

        return gameAdapterFactory.getGameAdapter(game).getMatches();
    }

    @GetMapping("/{game}")
    public GameDetails getGameDetails(@PathVariable GamesNames game){

        return gameAdapterFactory.getGameAdapter(game).getGameDetails();
    }



}
