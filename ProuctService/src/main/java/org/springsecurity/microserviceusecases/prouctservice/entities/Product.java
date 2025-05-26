package org.springsecurity.microserviceusecases.prouctservice.entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product extends BaseModel {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
}
