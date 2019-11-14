package com.sandbox.reactive.service;

import com.sandbox.reactive.document.PlayList;
import com.sandbox.reactive.repository.PlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class PlayListServiceImpl implements PlayListService{

    private PlayListRepository repository;

    @Override
    public Flux<PlayList> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<PlayList> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<PlayList> save(PlayList playlist) {
        return repository.save(playlist);
    }
}
