package com.sandbox.reactive.controller;


import com.sandbox.reactive.handler.CallListHandler;
import com.sandbox.reactive.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {

    @Autowired
    CallService service;

    @GetMapping("/start-call")
    public void startCall() throws InterruptedException {
        service.testCallQueue();
    }

}
