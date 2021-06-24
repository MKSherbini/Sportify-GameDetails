package com.example.gamedetails.controllers;

import com.example.gamedetails.adapter.NewsAdapter;
import com.example.gamedetails.models.dto.NewsDetailsDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.models.orm.News;
import com.example.gamedetails.repos.NewsJpaRepo;
import com.example.gamedetails.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@CrossOrigin
public class NewsController {
    private final NewsService newsService;
    private final NewsAdapter newsAdapter;
    private final NewsJpaRepo newsJpaRepo;

    public NewsController(NewsService newsService, NewsAdapter newsAdapter, NewsJpaRepo newsJpaRepo) {
        this.newsService = newsService;
        this.newsAdapter = newsAdapter;
        this.newsJpaRepo = newsJpaRepo;
    }

    @GetMapping
    public List<NewsDto> getAllNews() {
        return newsService.getNews();
    }


    @GetMapping("/{newsId}")
    public NewsDetailsDto getNews(@PathVariable Integer newsId) {
        return newsService.getNews(newsId);
    }

    @PostMapping
    public News addNews(@RequestBody NewsDto newsDto) {
        var news = newsAdapter.dtoToOrm(newsDto);
        newsJpaRepo.saveAndFlush(news);
        return news;
    }


}
