package org.springsecurity.microserviceusecases.prouctservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springsecurity.microserviceusecases.prouctservice.entities.Product;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findProductByProductId(Long productId);
}
