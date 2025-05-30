package org.springsecurity.microserviceusecases.springapigateway.exceptions;

public class AWSSecretKeyFetchException extends RuntimeException {
    public AWSSecretKeyFetchException(String nullAwsSecretKey) {
        super(nullAwsSecretKey);
    }
}
