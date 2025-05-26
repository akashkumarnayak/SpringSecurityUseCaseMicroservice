package org.springsecurity.microserviceusecases.authenticationauthorizationservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<User, Long> {
    Optional<User> findUsersByEmail(String email);
}
