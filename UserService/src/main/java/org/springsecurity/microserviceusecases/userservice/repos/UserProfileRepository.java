package org.springsecurity.microserviceusecases.userservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springsecurity.microserviceusecases.userservice.entities.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findUserProfileByEmail(String email);

}
