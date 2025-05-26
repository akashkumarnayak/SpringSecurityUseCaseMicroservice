package org.springsecurity.microserviceusecases.userservice.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class UserProfile extends BaseModel {

    private String name;
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(unique = true)
    private Long userId;
}
