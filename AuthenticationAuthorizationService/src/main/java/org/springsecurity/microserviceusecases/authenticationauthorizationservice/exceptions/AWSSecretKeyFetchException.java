package org.springsecurity.microserviceusecases.authenticationauthorizationservice.exceptions;

public class AWSSecretKeyFetchException extends RuntimeException {
    public AWSSecretKeyFetchException(String nullAwsSecretKey) {
        super(nullAwsSecretKey);
    }
}
