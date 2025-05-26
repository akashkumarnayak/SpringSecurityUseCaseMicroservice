package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponseDto {
    private String email;
    private String jwt;
}
