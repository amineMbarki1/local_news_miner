package com.newsminer.newsscraper.data;

import java.util.Date;


public class NewsArticle {
    final private String title;
    final private String source;
    final private String featuredImg;
    private String featuredVid;
    final private Date creationDate;
    final private String body;


    public NewsArticle(String title, String source, Date creationDate, String featuredImg, String body) {
        this.title = title;
        this.source = source;
        this.creationDate = creationDate;
        this.body = body;
        this.featuredImg = featuredImg;
    }

    public NewsArticle(String title, String source, Date creationDate, String featuredImg, String featuredVid, String body) {
        this(title, source, creationDate, featuredImg, body);
        this.featuredVid = featuredVid;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getFeaturedImg() {
        return featuredImg;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getFeaturedVid() {
        return featuredVid;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", featuredImg='" + featuredImg + '\'' +
                ", featuredVid='" + featuredVid + '\'' +
                ", creationDate=" + creationDate +
                ", body='" + body + '\'' +
                '}';
    }
}
