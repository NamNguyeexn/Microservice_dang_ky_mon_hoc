package com.soa.subject.DTO;

import lombok.Data;

@Data
public class ResponseObject<T> {
    private String message;
    private T data;
    public ResponseObject(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
