package com.mvr.plant.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class SseController {

    private final List<SseEmitter> clients = new CopyOnWriteArrayList<>();

    @GetMapping(path = "/updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeUpdates() {
        SseEmitter emitter = new SseEmitter(0L);
        clients.add(emitter);

        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));

        return emitter;
    }

    // 🔥 SEND SPECIFIC EVENT
    public void sendEvent(Object event) {
        List<SseEmitter> dead = new ArrayList<>();

        for (SseEmitter emitter : clients) {
            try {
                emitter.send(SseEmitter.event()
                        .name("DATA_UPDATE")
                        .data(event));
            } catch (Exception e) {
                dead.add(emitter);
            }
        }
        clients.removeAll(dead);
    }

}
