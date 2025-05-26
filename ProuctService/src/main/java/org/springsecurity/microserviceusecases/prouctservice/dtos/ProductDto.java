package org.springsecurity.microserviceusecases.prouctservice.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
}
