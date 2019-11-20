package com.sandbox.reactive;

import com.sandbox.reactive.document.PlayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

//@RunWith(SpringRunner.class)
@DataMongoTest

//@WebFluxTest
public class PlayListReactiveTest {

    @Autowired
    private ReactiveMongoTemplate template;


    @Test
    public void testReactiveDataBase(){
        ChangeStreamOptions options = ChangeStreamOptions.builder()
                .filter(
                        newAggregation(
                                PlayList.class,
                                Aggregation.match(Criteria.where("operationType").is("insert"))
                        )
                ).returnFullDocumentOnUpdate().build();

        Flux<PlayList> playList = template.changeStream("playList", options, PlayList.class)
                .map(ChangeStreamEvent::getBody)
                .doOnNext(p -> System.out.println("*** >>>> " + p));
        System.out.println("*** >>>> " + playList);
    }


}
