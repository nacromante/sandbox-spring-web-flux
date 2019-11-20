package com.sandbox.reactive.service;

import com.sandbox.reactive.document.Call;
import com.sandbox.reactive.document.PlayList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CallService {

    public Flux<Call> testReactiveDataBase();

    
}
