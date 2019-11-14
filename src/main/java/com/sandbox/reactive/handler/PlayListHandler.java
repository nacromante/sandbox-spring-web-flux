package com.sandbox.reactive.handler;


import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PlayListHandler {

    private PlayListService service;


    public Flux<PlayList> findAll() {
        return service.findAll();
    }

    public Mono<PlayList> findById(String id) {
        return service.findById(id);
    }

    public Mono<PlayList> save(PlayList playlist) {
        return service.save(playlist);
    }
}
