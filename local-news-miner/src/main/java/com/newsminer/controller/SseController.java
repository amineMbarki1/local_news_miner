package com.newsminer.controller;


import com.newsminer.service.ArticlesScrapedEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/sse")
public class SseController {
    private Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping
    public SseEmitter eventEmitter() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        UUID guid = UUID.randomUUID();
        sseEmitters.put(guid.toString(), sseEmitter);
        sseEmitter.send(SseEmitter.event().name("NEW_SEE").data(guid));
        sseEmitter.onCompletion(() -> sseEmitters.remove(guid.toString()));
        sseEmitter.onTimeout(() -> sseEmitters.remove(guid.toString()));
        return sseEmitter;
    }


    @EventListener(ArticlesScrapedEvent.class)
    public void notifyClients(ArticlesScrapedEvent articlesScrapedEvent)  {
        System.out.println("notify clients");
        for (Map.Entry<String, SseEmitter> entry : sseEmitters.entrySet()) {
            SseEmitter sseEmitter = entry.getValue();
            try {
                sseEmitter.send(SseEmitter.event().name("Amine").data("Updated Content :)"));
            } catch (IOException e) {
                System.out.println("error");
//                e.printStackTrace();
                sseEmitter.completeWithError(e);

            }
//            sseEmitter.complete();
        }


    }
}
