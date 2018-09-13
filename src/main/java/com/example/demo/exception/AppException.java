package com.example.demo.exception;

import lombok.Getter;

public class AppException extends RuntimeException {
    @Getter
    private final String[] args;

    public AppException(String msg) {
        this(msg, new String[0]);
    }

    public AppException(String msg, String... args) {
        super(msg);
        this.args = args;
    }

}
