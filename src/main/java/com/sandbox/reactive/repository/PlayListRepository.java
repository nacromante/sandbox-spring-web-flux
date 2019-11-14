package com.sandbox.reactive.repository;

import com.sandbox.reactive.document.PlayList;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PlayListRepository extends ReactiveMongoRepository<PlayList, String> {
}
