package com.newsminer.repository;

import com.newsminer.entity.NewsArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticleEntity, Long> {

    boolean existsNewsArticleByTitle(String title);

    List<NewsArticleEntity> findAllByOrderByCreationDateDesc();

}
