package com.newsminer.newsscraper.retrievers;


import com.newsminer.newsscraper.JsoupRetriever;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

//SCRAPE CONTENT FROM www.mosaiquefm.net
@Component
public class MosRetriever extends JsoupRetriever {
    private static final String FRONT_PAGE_URL_NATIONAL = "https://www.mosaiquefm.net/fr/actualites/actualite-national-tunisie/1";
    private final static String ARTICAL_CARD_SELECTOR = "section.homeNews2 > div.row > div";
    private final static String SOURCE = "mosaique";


    public MosRetriever() {
        super(FRONT_PAGE_URL_NATIONAL, ARTICAL_CARD_SELECTOR, SOURCE);
    }

    @Override
    public Set<URL> retrieveArticlesLinks(Document doc) {

        return doc.select(this.articalSelector).stream().map(e -> {
            try {
                return new URL(e.attr("data-vr-contentbox-url"));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toSet());
    }

    @Override
    public String retrieveTitle(Document doc) {
        return doc.select("article > h1").first().text();
    }

    @Override
    public Date retrieveDateOfCreation(Document doc) {
//        "2023-02-02 13:14:00"
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(doc.select("article").select("time").attr("datetime"));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public String retrieveBody(Document doc) {
        return doc.select("article").select("p").outerHtml();
    }

    @Override
   public String retrieveFeaturedVid(Document doc) {
//        TODO: retreive video src
        return null;
    }

    @Override
   public String retrieveFeaturedImage(Document doc) {
        Element img = doc.select("article").select("figure").select("img").first();
        try {
            return img.attr("data-srcset").split(" ")[0];
        } catch (NullPointerException ex) {
            return "no image, it's a featured Video";
        }
    }
}
