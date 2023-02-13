package com.newsminer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Table(name = "news_article")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NewsArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String source;

    @Column(columnDefinition = "LONGTEXT")
    private String featuredImg;

    @Column(columnDefinition = "LONGTEXT")
    private String featuredVid;

    private Date creationDate;
    @Column(columnDefinition = "LONGTEXT")
    private String body;
}
