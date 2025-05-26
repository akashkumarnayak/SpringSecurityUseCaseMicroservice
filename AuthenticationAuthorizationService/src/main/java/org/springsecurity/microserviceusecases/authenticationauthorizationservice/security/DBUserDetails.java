package org.springsecurity.microserviceusecases.authenticationauthorizationservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.User;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.UserState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBUserDetails implements UserDetails {

    private User user;

    public DBUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(List.of(new SimpleGrantedAuthority("ROLE_"+user.getUserType().name())));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserState.ACTIVE.equals(user.getUserState());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !UserState.LOCKED.equals(user.getUserState());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserState.ACTIVE.equals(user.getUserState());
    }

    @Override
    public boolean isEnabled() {
        return UserState.ACTIVE.equals(user.getUserState());
    }
}
