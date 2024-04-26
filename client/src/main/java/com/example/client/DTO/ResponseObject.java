package com.example.client.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseObject<T> {
    private String message;
    private T data;

    public ResponseObject(String message, T data) {
        this.message = message;
        this.data = data;
    }
    @JsonCreator
    public ResponseObject(@JsonProperty("data") T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "" + data;
    }
}
