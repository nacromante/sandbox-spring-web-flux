package com.sandbox.reactive.repository;

import com.sandbox.reactive.document.Call;
import com.sandbox.reactive.document.PlayList;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CallRepository extends ReactiveMongoRepository<Call, String> {
}
