package com.sandbox.reactive.document;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class Doctor {

    private Integer code;
    private String name;
    private Map<String, Integer> mapNumberOfCalls;


    public void incrementNumberCall(String uuid){
        Integer number = this.mapNumberOfCalls.get(uuid);
        number = number == null ? 1 : number;
        number++;
        this.mapNumberOfCalls.put(uuid, number);
    }

    public Integer getNumberOfCalls(String uuid){
        return this.mapNumberOfCalls.get(uuid) == null ? 1 : 0;
    }

}
