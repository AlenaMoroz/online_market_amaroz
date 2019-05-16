package com.gmail.marozalena.onlinemarket.service.exception;

public class UsersNotDeletedException extends RuntimeException {
    public UsersNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
