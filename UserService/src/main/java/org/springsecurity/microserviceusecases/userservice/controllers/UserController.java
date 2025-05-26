package org.springsecurity.microserviceusecases.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springsecurity.microserviceusecases.userservice.dtos.AddressDto;
import org.springsecurity.microserviceusecases.userservice.dtos.UserProfileDto;
import org.springsecurity.microserviceusecases.userservice.entities.Address;
import org.springsecurity.microserviceusecases.userservice.entities.UserProfile;
import org.springsecurity.microserviceusecases.userservice.services.UserProfileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserProfileService userProfileService;


    @PutMapping("/user/update")
    public UserProfileDto changeUserDetails(@RequestBody UserProfileDto userProfileDto)
    {
        return from(userProfileService.updateUser(from(userProfileDto)));
    }

    @GetMapping("/user/get-all")
    public List<UserProfileDto> getAllUsers()
    {
        List<UserProfile> userProfiles = userProfileService.getAllUser();

        return userProfiles.stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{email}")
    public UserProfileDto getUserByEmail(@PathVariable String email)
    {
        return from(userProfileService.getUserByEmail(email));

    }

    @PostMapping("/user/create")
    public UserProfileDto createUser(@RequestBody UserProfileDto userProfileDto)
    {
        return from(userProfileService.createUser(from(userProfileDto)));
    }

    UserProfile from(UserProfileDto userProfileDto)
    {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(userProfileDto.getName());
        userProfile.setPhoneNumber(userProfileDto.getPhoneNumber());
        userProfile.setEmail(userProfileDto.getEmail());
        userProfile.setUserId(userProfileDto.getUserId());

        Address address = new Address();
        address.setAddress(userProfileDto.getAddressDto().getAddress1());
        address.setCity(userProfileDto.getAddressDto().getCity());
        address.setState(userProfileDto.getAddressDto().getState());
        address.setCountry(userProfileDto.getAddressDto().getCountry());
        address.setPinCode(userProfileDto.getAddressDto().getPinCode());

        userProfile.setAddress(address);
        return userProfile;
    }

    UserProfileDto from(UserProfile userProfile)
    {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setName(userProfile.getName());
        userProfileDto.setPhoneNumber(userProfile.getPhoneNumber());
        userProfileDto.setEmail(userProfile.getEmail());
        userProfileDto.setUserId(userProfile.getUserId());
        AddressDto addressDto = new AddressDto();
        addressDto.setAddress1(userProfile.getAddress().getAddress());
        addressDto.setCity(userProfile.getAddress().getCity());
        addressDto.setState(userProfile.getAddress().getState());
        addressDto.setCountry(userProfile.getAddress().getCountry());
        addressDto.setPinCode(userProfile.getAddress().getPinCode());
        userProfileDto.setAddressDto(addressDto);
        return userProfileDto;
    }
}
