package com.example.demo.application.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource) {
        super(resource + " not found");
    }
}

