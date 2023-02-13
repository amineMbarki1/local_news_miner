package com.newsminer.newsscraper;

import com.newsminer.newsscraper.data.NewsArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CombinedRetriever implements ICombinedRetriever {
    private List<JsoupRetriever> retrievers;

    @Autowired
    public CombinedRetriever(List<JsoupRetriever> retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public List<NewsArticle> retrieve() {
        List<NewsArticle> retrievedArticles = retrievers.stream().flatMap(retriever -> {
            try {
                return retriever.retrieveArticles().stream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return retrievedArticles;
    }

}
