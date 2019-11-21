package com.sandbox.reactive.document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Doctor {

    private Integer code;
    private String name;

}
