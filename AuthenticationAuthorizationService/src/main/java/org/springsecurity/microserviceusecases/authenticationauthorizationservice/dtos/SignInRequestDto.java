package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import lombok.Data;

@Data
public class SignInRequestDto {
    private String email;
    private String password;
}
