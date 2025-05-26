package org.springsecurity.microserviceusecases.userservice.dtos;

import lombok.Data;

@Data
public class AddressDto {
    private String address1;
    private String city;
    private String state;
    private String country;
    private Long pinCode;
}
