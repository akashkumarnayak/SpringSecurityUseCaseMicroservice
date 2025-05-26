package org.springsecurity.microserviceusecases.authenticationauthorizationservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.*;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.entities.User;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.services.UserAuthService;

@RestController
@RequestMapping("/v1/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/signup")
    public String signUp(@RequestBody UserSignUpRequestDto userSignUpRequestDto)
    {
        userAuthService.signUp(from(userSignUpRequestDto),userSignUpRequestDto.getUserProfileDto());
        return "Sign Up Successful";
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInRequestDto signInRequestDto)
    {
        String jwt = userAuthService.signIn(signInRequestDto);
        return new SignInResponseDto(signInRequestDto.getEmail(), jwt);
    }

    @PostMapping("/signout")
    public String signOut(@RequestBody SignOutRequestDto signOutRequestDto)
    {
        userAuthService.signout(signOutRequestDto);
        return "Sign Out Successful";
    }

    User from(UserSignUpRequestDto userSignUpRequestDto)
    {
        User user = new User();
        user.setEmail(userSignUpRequestDto.getEmail());
        user.setPassword(userSignUpRequestDto.getPassword());
        user.setUserType(userSignUpRequestDto.getUserType());
        return user;
    }

}
