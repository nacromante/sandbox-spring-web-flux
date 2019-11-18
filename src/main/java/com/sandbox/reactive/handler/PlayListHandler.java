package com.sandbox.reactive.handler;


import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.service.PlayListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.Collectors;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class PlayListHandler {

    @Autowired
    private PlayListService service;


    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        Flux<Tuple2<Long, PlayList>> tuple2Flux = Flux.interval(Duration.ofSeconds(5)).zipWith(service.findAll());
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(tuple2Flux, PlayList.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id), PlayList.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<PlayList> playListMono = serverRequest.bodyToMono(PlayList.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body( fromPublisher(playListMono.flatMap(service::save), PlayList.class));
    }
}
