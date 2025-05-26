package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import lombok.Data;

@Data
public class UserProfileDto {
    private String name;
    private String phoneNumber;
    private String email;
    private AddressDto addressDto;
    private Long userId;
}
