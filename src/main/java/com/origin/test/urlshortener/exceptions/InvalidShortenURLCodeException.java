package com.origin.test.urlshortener.exceptions;

public class InvalidShortenURLCodeException extends RuntimeException {

    public InvalidShortenURLCodeException(String message) {
        super(message);
    }
}
