package org.springsecurity.microserviceusecases.userservice.services;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springsecurity.microserviceusecases.userservice.entities.Address;
import org.springsecurity.microserviceusecases.userservice.entities.UserProfile;
import org.springsecurity.microserviceusecases.userservice.exception.UserProfileDoesNotExistException;
import org.springsecurity.microserviceusecases.userservice.repos.UserProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserProfile updateUser(UserProfile userProfile) {

        Optional<UserProfile> userProfileStored = userProfileRepository.findUserProfileByEmail(userProfile.getEmail());

        if (userProfileStored.isPresent()) {
            userProfileStored.get().setEmail(userProfile.getEmail());
            userProfileStored.get().setName(userProfile.getName());
            userProfileStored.get().setPhoneNumber(userProfile.getPhoneNumber());
            Address storedAddress = userProfileStored.get().getAddress();
            storedAddress.setAddress(userProfile.getAddress().getAddress());
            storedAddress.setCity(userProfile.getAddress().getCity());
            storedAddress.setState(userProfile.getAddress().getState());
            storedAddress.setCountry(userProfile.getAddress().getCountry());
            storedAddress.setPinCode(userProfile.getAddress().getPinCode());
            userProfileStored.get().setAddress(userProfile.getAddress());
            userProfileRepository.save(userProfileStored.get());
        }
        else
        {
            throw new UserProfileDoesNotExistException("User profile for user "+ userProfile.getEmail() +"does not exist");
        }

        return userProfile;
    }

    public List<UserProfile> getAllUser() {

        return userProfileRepository.findAll();
    }

    public UserProfile getUserByEmail(String email) {

        Optional<UserProfile> storedUserProfile = userProfileRepository.findUserProfileByEmail(email);
        if (storedUserProfile.isPresent()) {
            return storedUserProfile.get();
        }
        else
        {
            throw new UserProfileDoesNotExistException("User profile for user "+ email +" does not exist");
        }
    }

    public UserProfile createUser(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }
}
