package com.mvr.plant.controller;
import com.mvr.plant.DTO.SseEvent;
import com.mvr.plant.DTO.SseEntities;
import com.mvr.plant.DTO.SseActions;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
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
        emitter.onError(e -> clients.remove(emitter));

        return emitter;
    }

    public void sendEvent(SseEvent event) {

        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : clients) {
            try {

                emitter.send(
                        SseEmitter.event()
                                .id(String.valueOf(System.currentTimeMillis()))
                                .name("DATA_UPDATE")
                                .data(event)
                                .reconnectTime(3000)
                );

                //System.out.println("SSE EVENT SENT: " + event);

            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        }

        clients.removeAll(deadEmitters);
    }

    @Scheduled(fixedRate = 20000)
    public void heartbeat() {
        sendEvent(new SseEvent(SseEntities.PING, SseActions.KEEP_ALIVE, null, null));
    }
}