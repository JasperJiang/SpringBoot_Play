package com.example.demo.exception;

import lombok.Getter;

/**
 * Created by jbelligund001 on 7/3/2016.
 */
public class DuplicateException extends RuntimeException {

    @Getter
    private final String[] args;

    public DuplicateException(String msg) {
        this(msg, new String[0]);
    }

    public DuplicateException(String msg, String... args) {
        super(msg);
        this.args = args;
    }
}
