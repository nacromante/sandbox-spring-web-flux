package com.sandbox.reactive.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Call {
    @Id
    private String uuid;
    private Integer careProviderCode;
    private Integer requestedUserCode;
    private CallState callState;


}
