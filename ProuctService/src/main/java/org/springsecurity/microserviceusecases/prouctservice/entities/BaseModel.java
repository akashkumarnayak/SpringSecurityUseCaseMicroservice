package org.springsecurity.microserviceusecases.prouctservice.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseModel {

    @Id
    public Long productId;
    public LocalDateTime createdOn;
    public LocalDateTime lastUpdatedOn;

    public BaseModel()
    {
        createdOn = LocalDateTime.now();
        lastUpdatedOn = LocalDateTime.now();
    }
}
