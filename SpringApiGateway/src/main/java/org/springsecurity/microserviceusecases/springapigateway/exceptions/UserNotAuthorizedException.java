package org.springsecurity.microserviceusecases.springapigateway.exceptions;

public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException(String notAuthorized) {
        super(notAuthorized);
    }
}
