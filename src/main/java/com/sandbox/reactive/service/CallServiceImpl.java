package com.sandbox.reactive.service;

import com.sandbox.reactive.document.Call;
import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.repository.CallRepository;
import com.sandbox.reactive.repository.PlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@AllArgsConstructor
@Service
public class CallServiceImpl implements CallService{

    private CallRepository repository;

    @Autowired
    private ReactiveMongoTemplate template;

    @Override
    public Flux<Call> testReactiveDataBase() {
        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(
                        newAggregation(
                                Call.class,
                                Aggregation.match(Criteria.where("operationType").is("insert"))
                        )
                ).returnFullDocumentOnUpdate().build();

        Flux<Call> callFlux = template.changeStream("playList", options, Call.class)
                .map(ChangeStreamEvent::getBody)
                .doOnNext(p -> System.out.println("*** >>>> " + p));
        return callFlux;
    }
}
