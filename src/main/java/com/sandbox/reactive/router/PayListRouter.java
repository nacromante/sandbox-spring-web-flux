package com.sandbox.reactive.router;

import com.sandbox.reactive.handler.PlayListHandler;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PayListRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PlayListHandler handler){

        return RouterFunctions
                .route(GET("/playlist").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/playlist/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(POST("/playlist").and(accept(MediaType.APPLICATION_JSON)), handler::save);
    }

}
