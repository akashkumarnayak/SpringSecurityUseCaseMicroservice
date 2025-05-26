package org.springsecurity.microserviceusecases.userservice.exception;

public class UserProfileDoesNotExistException extends RuntimeException {
    public UserProfileDoesNotExistException(String s) {
        super(s);
    }
}
