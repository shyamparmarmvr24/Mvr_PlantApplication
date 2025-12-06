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
        SseEmitter emitter = new SseEmitter(0L);  // never timeout
        clients.add(emitter);

        emitter.onCompletion(() -> clients.remove(emitter));
        emitter.onTimeout(() -> clients.remove(emitter));

        return emitter;
    }

    // 🔥 Notify all browsers
    public void broadcastUpdate() {
        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : clients) {
            try {
                emitter.send(SseEmitter.event().data("update"));
            } catch (IOException e) {
                deadEmitters.add(emitter);  // mark dead client
            }
        }

        clients.removeAll(deadEmitters);   //  remove disconnected clients
    }

}
