package com.sandbox.reactive.router;

import com.sandbox.reactive.handler.PlayListHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Predicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PayListRouter {

//    public RouterFunction<ServerResponse> route(PlayListHandler handler){
//        RouterFunctions
//                .route(GET("/playlist").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
//                .andRoute(GET("/playlist/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
//                .andRoute(POST("/playlist").and(accept(MediaType.APPLICATION_JSON)), handler::save);
//    }

}
