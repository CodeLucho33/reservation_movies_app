package com.example.reservation_movies_app.exception;

public class AlreadyExistsException  extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
