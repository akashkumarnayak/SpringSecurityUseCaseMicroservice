package org.springsecurity.microserviceusecases.authenticationauthorizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthenticationAuthorizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationAuthorizationServiceApplication.class, args);
    }

}
