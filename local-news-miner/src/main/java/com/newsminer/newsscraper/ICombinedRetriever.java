package com.newsminer.newsscraper;

import com.newsminer.newsscraper.data.NewsArticle;

import java.util.List;

public interface ICombinedRetriever {
    List<NewsArticle> retrieve();
}
