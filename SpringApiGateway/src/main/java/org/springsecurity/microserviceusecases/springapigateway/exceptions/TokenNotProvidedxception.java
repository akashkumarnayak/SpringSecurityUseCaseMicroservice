package org.springsecurity.microserviceusecases.springapigateway.exceptions;

public class TokenNotProvidedxception extends RuntimeException {
    public TokenNotProvidedxception(String tokenNotProvidedForAuthentication) {
        super(tokenNotProvidedForAuthentication);
    }
}
