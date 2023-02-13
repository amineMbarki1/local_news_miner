package com.newsminer.service;

import org.springframework.context.ApplicationEvent;

public class ArticlesScrapedEvent extends ApplicationEvent {
    private String message;

    public ArticlesScrapedEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
