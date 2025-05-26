package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import lombok.Data;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.User;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.UserType;

@Data
public class UserSignUpRequestDto {
    private String email;
    private String Password;
    private UserType userType;
    private UserProfileDto userProfileDto;
}
