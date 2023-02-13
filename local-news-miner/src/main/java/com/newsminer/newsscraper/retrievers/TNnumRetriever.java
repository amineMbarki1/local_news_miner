package com.newsminer.newsscraper.retrievers;

import com.newsminer.newsscraper.JsoupRetriever;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;


//SCRAPE CONTENT FROM tunisienumerique.com
@Component
public class TNnumRetriever extends JsoupRetriever {

    private final static String FRONT_PAGE_URL_TUNISIE = "https://www.tunisienumerique.com/actualite-tunisie/tunisie/";
    private final static String ARTICLE_CARD_SELECTOR = "#archive-list-wrap > ul > li";

    private final static String SOURCE = "tunisie_numérique";


    public TNnumRetriever() {
        super(FRONT_PAGE_URL_TUNISIE, ARTICLE_CARD_SELECTOR, SOURCE);
    }


    @Override
    public Set<URL> retrieveArticlesLinks(Document doc) {
        return doc.select(ARTICLE_CARD_SELECTOR).select("a").stream().map(e -> {
            try {
                return new URL(e.attr("href"));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }).collect(Collectors.toSet());
    }


    @Override
    public String retrieveFeaturedVid(Document doc) {
        Elements videoWrapper = doc.select("#video-embed");
        if (videoWrapper.isEmpty()) return null;
        return videoWrapper.select("iframe").attr("src");
    }

    @Override
    public String retrieveBody(Document doc) {
        return doc.select("#content-main").select("p").outerHtml();

    }

    @Override
    public String retrieveFeaturedImage(Document doc) {
        String featuredImg = doc.select("#post-feat-img").select("img").attr("src");
        Elements videoWrapper = doc.select("#video-embed");
        if (!videoWrapper.isEmpty()) {
            String frameSrc = videoWrapper.select("iframe").attr("src");
            try {
                featuredImg = Jsoup.connect(frameSrc).get().select("img").first().attr("src");
            } catch (Exception exception) {
                System.out.println("No feature Img :/");
            }
        }
        return featuredImg;
    }

    @Override
    public String retrieveTitle(Document doc) {
        return doc.select("#post-area").select(".post-title").first().text();

    }

    @Override
    public Date retrieveDateOfCreation(Document doc) {
        String date = doc.getElementsByTag("time").text();
        return parseDate(date);
    }

    private Date parseDate(String date) {
        if (date.substring(2).startsWith("I")) {
            String[] sub = date.substring(9).split(" ");
            int x = Integer.parseInt(sub[0]);
            long milliSecondsAgo = sub[1].startsWith("s") ? 1000 : sub[1].startsWith("m") ? 60 * 1000 : 3600 * 1000;
            return new Date(System.currentTimeMillis() - milliSecondsAgo * x);
        } else {
            try {
                return new SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.FRENCH)
                        .parse(date.substring(2).replaceAll("à", ""));
            } catch (ParseException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

}
