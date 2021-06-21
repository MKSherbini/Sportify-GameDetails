package com.example.gamedetails.service;

import com.example.gamedetails.adapter.NewsAdapter;
import com.example.gamedetails.models.dto.NewsDetailsDto;
import com.example.gamedetails.models.dto.NewsDto;
import com.example.gamedetails.repos.NewsJpaRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final NewsJpaRepo newsJpaRepo;
    private final NewsAdapter newsAdapter;

    public NewsService(NewsJpaRepo newsJpaRepo, NewsAdapter newsAdapter) {
        this.newsJpaRepo = newsJpaRepo;
        this.newsAdapter = newsAdapter;
    }

    public List<NewsDto> getNews() {
        return newsJpaRepo.findAll().stream()
                .map(newsAdapter::ormToDto).collect(Collectors.toList());
    }

    public NewsDetailsDto getNews(Integer id) {
        return newsAdapter.ormToNewsDetailsDto(newsJpaRepo.getById(id));
    }
}
