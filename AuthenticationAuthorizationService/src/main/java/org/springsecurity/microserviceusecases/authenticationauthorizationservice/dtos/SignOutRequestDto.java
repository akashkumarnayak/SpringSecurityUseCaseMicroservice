package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class SignOutRequestDto {
    private String username;
    private String jti;
    Date expiryDate;
}
