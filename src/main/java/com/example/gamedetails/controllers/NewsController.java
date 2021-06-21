package com.example.gamedetails.controllers;

import com.example.gamedetails.models.dto.NewsDetailsDto;
import com.example.gamedetails.service.NewsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/{newsId}")
    public NewsDetailsDto getNews(@PathVariable Integer newsId) {
        return newsService.getNews(newsId);
    }




}
