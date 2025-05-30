package org.springsecurity.microserviceusecases.springapigateway.dtos;

import lombok.Data;

@Data
public class AwsSecretManager {
    private String jwtSecretKey;
}
