package org.springsecurity.microserviceusecases.authenticationauthorizationservice.security;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.repos.UserAuthRepository;

import java.util.Optional;

@Service
public class DBUserDetailService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userAuthRepository.findUsersByEmail(username);
        if (user.isPresent()) {
            return new DBUserDetails(user.get());
        }

        throw new UsernameNotFoundException(username);
    }
}
