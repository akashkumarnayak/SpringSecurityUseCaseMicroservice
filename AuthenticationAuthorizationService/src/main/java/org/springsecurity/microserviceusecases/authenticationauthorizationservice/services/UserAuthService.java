package org.springsecurity.microserviceusecases.authenticationauthorizationservice.services;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.configs.UserProfileClient;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.SignInRequestDto;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.SignOutRequestDto;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.UserProfileDto;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.UserSignUpRequestDto;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.User;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.exceptions.UserCreationException;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.repos.UserAuthRepository;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.security.DBUserDetailService;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.security.DBUserDetails;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.security.JwtService;

import java.util.concurrent.TimeUnit;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserProfileClient userProfileClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(User user, UserProfileDto userProfileDto)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savededUser = userAuthRepository.save(user);
        userProfileDto.setUserId(savededUser.getId());

        try{
            UserProfileDto userProfileDto1 = userProfileClient.createUserProfile(userProfileDto);
        }
        catch(FeignException ex){
            userAuthRepository.delete(savededUser);
          throw new UserCreationException("User Profile creation failed");
        }
    }

    public String signIn(SignInRequestDto signInRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequestDto.getEmail());
        return jwtService.generateToken(userDetails);
    }

    public void signout(SignOutRequestDto signOutRequestDto) {

        long ttlMillis = signOutRequestDto.getExpiryDate().getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(signOutRequestDto.getJti(), "blacklisted", ttlMillis, TimeUnit.MILLISECONDS);
    }
}
