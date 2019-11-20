package com.sandbox.reactive.handler;


import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Component
public class PlayListHandler {

    @Autowired
    private PlayListService service;

    @Autowired
    private ReactiveMongoTemplate template;


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
    public Mono<ServerResponse> findByReact(ServerRequest serverRequest) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(testReactiveDataBase(), PlayList.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<PlayList> playListMono = serverRequest.bodyToMono(PlayList.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body( fromPublisher(playListMono.flatMap(service::save), PlayList.class));
    }


    public Flux<PlayList> testReactiveDataBase(){


//        template.changeStream(
//                newAggregation( match ),
//                PlayList.class,
//                ChangeStreamOptions.empty(),
//                "playList"
//                );

        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(
                        newAggregation(
                                PlayList.class,
                                Aggregation.match(Criteria.where("operationType").is("insert"))
                        )
                ).returnFullDocumentOnUpdate().build();

        Flux<PlayList> playList = template.changeStream("playList", options, PlayList.class)
                .map(ChangeStreamEvent::getBody)
//                .doOnNext(System.out::println);
                .doOnNext(p -> System.out.println("*** >>>> " + p));
        return playList;
    }
}
