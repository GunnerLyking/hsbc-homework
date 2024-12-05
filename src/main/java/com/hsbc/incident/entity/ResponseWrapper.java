package com.hsbc.incident.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapper<T> {

    private String message;
    private T data;
    private int statusCode;

    public ResponseWrapper(String message, T data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }

    public static <T> ResponseWrapper<T> ok(T data) {
        return new ResponseWrapper<>("OK", data, 200);
    }
}

