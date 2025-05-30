package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AwsSecretManager {
    private String jwtSecretKey;
}
