package com.example.gamedetails.controllers;

import com.example.gamedetails.models.News;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameDetailsController {

    @GetMapping("/{game}/news")
    public List<News> getGameNews(@PathVariable String game){

        return new ArrayList<>();
    }


}
