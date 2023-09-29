package com.moreffnest.usersapi.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Couldn't find user with id = " + id);
    }
}
