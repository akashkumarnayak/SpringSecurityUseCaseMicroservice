package org.springsecurity.microserviceusecases.authenticationauthorizationservice.configs;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.UserProfileDto;

@FeignClient(name = "UserService")
public interface UserProfileClient {

    @PostMapping("/v1/user/create")
    public UserProfileDto createUserProfile(@RequestBody UserProfileDto userProfileDto);
}
