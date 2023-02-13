package com.newsminer.controller;

import com.newsminer.entity.NewsArticleEntity;
import com.newsminer.service.NewsScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news-feed")
public class NewsFeedController {

    private final NewsScraperService newsScraperService;

    public NewsFeedController(NewsScraperService newsScraperService) {
        this.newsScraperService = newsScraperService;
    }

    @GetMapping
    public List<NewsArticleEntity> getNewsArticles() {
        return newsScraperService.getNewsArticles();
    }


}
