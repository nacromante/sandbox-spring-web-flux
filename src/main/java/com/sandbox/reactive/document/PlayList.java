package com.sandbox.reactive.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class PlayList {
    @Id
    private String id;

    private String name;


}
