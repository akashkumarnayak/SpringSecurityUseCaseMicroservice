package org.springsecurity.microserviceusecases.prouctservice.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springsecurity.microserviceusecases.prouctservice.dtos.ProductDto;
import org.springsecurity.microserviceusecases.prouctservice.entities.Product;
import org.springsecurity.microserviceusecases.prouctservice.exceptions.ProductDoesNotExistException;
import org.springsecurity.microserviceusecases.prouctservice.repositories.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product updateProduct(Product product) {

        Optional<Product> productFromDb = productRepo.findProductByProductId(product.getProductId());

        if (productFromDb.isPresent()) {
            productRepo.save(product);
        }
        else
        {
            throw new ProductDoesNotExistException("product" + product.getProductName() +"does not exist");
        }

        return product;
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }


    public void deleteProduct(long productId) {
        productRepo.deleteById(productId);
    }
}
