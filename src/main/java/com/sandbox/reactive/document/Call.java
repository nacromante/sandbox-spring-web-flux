package com.sandbox.reactive.document;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
//@RequiredArgsConstructor
@Document
@Data
public class Call {
    @Id
    private String uuid;
    private Doctor doctor;
    private Integer requestedUserCode;
    private CallState callState;


}
