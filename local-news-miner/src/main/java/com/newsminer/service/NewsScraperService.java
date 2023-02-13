package com.newsminer.service;

import com.newsminer.entity.NewsArticleEntity;
import com.newsminer.newsscraper.ICombinedRetriever;
import com.newsminer.newsscraper.data.NewsArticle;
import com.newsminer.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class NewsScraperService {
    private final ICombinedRetriever combinedRetriever;
    private final NewsArticleRepository newsArticleRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public NewsScraperService(ICombinedRetriever combinedRetriever, NewsArticleRepository newsArticleRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.newsArticleRepository = newsArticleRepository;
        this.combinedRetriever = combinedRetriever;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void scrapeThenSaveArticles() {
        System.out.println("hey i'm scraping articles");
        List<NewsArticle> retrievedArticles = combinedRetriever.retrieve();

        List<NewsArticleEntity> collect = retrievedArticles.stream().map(this::createNewsArticleEntity).collect(Collectors.toList());
        collect.forEach(e -> {
            if (!newsArticleRepository.existsNewsArticleByTitle(e.getTitle())) newsArticleRepository.save(e);
        });
//        newsArticleRepository.saveAll(collect);
        System.out.println("Finished :)");

//        TODO: Notify client
        applicationEventPublisher.publishEvent(new ArticlesScrapedEvent(this, "Scraped Articles and saved to database"));
    }

    private NewsArticleEntity createNewsArticleEntity(NewsArticle newsArticle) {
        return NewsArticleEntity.builder().title(newsArticle.getTitle())
                .creationDate(newsArticle.getCreationDate())
                .source(newsArticle.getSource())
                .featuredImg(newsArticle.getFeaturedImg())
                .featuredVid(newsArticle.getFeaturedVid())
                .body(newsArticle.getBody()).build();
    }

    public List<NewsArticleEntity> getNewsArticles() {
        return newsArticleRepository.findAllByOrderByCreationDateDesc();
    }
}
