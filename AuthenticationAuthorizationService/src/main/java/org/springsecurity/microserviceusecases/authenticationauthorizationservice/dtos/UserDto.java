package org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.UserState;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.UserType;

@Data
public class UserDto {

    private String email;
    private String Password;
    private UserType userType;
    private UserProfileDto userProfileDto;

}
