package com.newsminer.newsscraper;

import com.newsminer.newsscraper.data.NewsArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class JsoupRetriever {
    public URL frontPageURL;
    public String articalSelector;
    public String source;

    public JsoupRetriever(String url, String articalSelector, String source) {
        try {
            this.frontPageURL = new URL(url);
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
        this.articalSelector = articalSelector;
        this.source = source;
    }

    public Set<NewsArticle> retrieveArticles() throws IOException {

        try {
            Document doc = Jsoup.connect(this.frontPageURL.toString()).get();
            Set<URL> articalesLinks = this.retrieveArticlesLinks(doc);
            return articalesLinks.stream().map(this::retriveArticalFromURL).collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private NewsArticle retriveArticalFromURL(URL url) {
        try {
            Document articalDoc = Jsoup.connect(url.toString()).get();
            return this.retrieveArticle(articalDoc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public NewsArticle retrieveArticle(Document doc) throws IOException {
        String title = retrieveTitle(doc);
        Date dateOfCreation = retrieveDateOfCreation(doc);
        String body = retrieveBody(doc);
        String featuredVid = retrieveFeaturedVid(doc);
        String featuredImg = retrieveFeaturedImage(doc);
        if (featuredVid != null)
            return new NewsArticle(title, this.source, dateOfCreation, featuredImg, featuredVid, body);
        return new NewsArticle(title, this.source, dateOfCreation, featuredImg, body);
    }

    public abstract Set<URL> retrieveArticlesLinks(Document doc) throws MalformedURLException;

    public abstract String retrieveTitle(Document doc);

    public abstract Date retrieveDateOfCreation(Document doc);

    public abstract String retrieveBody(Document doc);

    public abstract String retrieveFeaturedVid(Document doc);

    public abstract String retrieveFeaturedImage(Document doc);

}
