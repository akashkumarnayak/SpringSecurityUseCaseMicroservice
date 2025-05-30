package org.springsecurity.microserviceusecases.springapigateway.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String invalidTokenProvided) {
        super(invalidTokenProvided);
    }
}
