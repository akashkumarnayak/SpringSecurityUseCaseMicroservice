package org.springsecurity.microserviceusecases.userservice.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDate;

@Data
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected LocalDate createdDate;
    protected LocalDate lastModifiedDate;

    public BaseModel() {
        createdDate = LocalDate.now();
        lastModifiedDate = LocalDate.now();
    }
}
