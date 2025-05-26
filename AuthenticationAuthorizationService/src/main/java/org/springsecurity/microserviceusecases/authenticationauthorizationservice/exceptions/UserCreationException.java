package org.springsecurity.microserviceusecases.authenticationauthorizationservice.exceptions;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String userProfileCreationFailed) {
        super(userProfileCreationFailed);
    }
}
