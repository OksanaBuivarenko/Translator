package com.example.lab.exception;

public class LanguageNotFoundException extends RuntimeException {

    public LanguageNotFoundException(String message) {
        super(message);
    }
}