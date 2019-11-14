package com.sandbox.reactive.service;

import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.handler.PlayListHandler;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayListService {

    public Flux<PlayList> findAll();
    public Mono<PlayList> findById(String id);
    public Mono<PlayList> save(PlayList playlist);
}
