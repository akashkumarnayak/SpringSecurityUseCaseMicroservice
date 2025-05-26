package org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String Password;

    private UserType userType;
    private UserState userState;

    public User()
    {
        userState=UserState.ACTIVE;
    }

}
