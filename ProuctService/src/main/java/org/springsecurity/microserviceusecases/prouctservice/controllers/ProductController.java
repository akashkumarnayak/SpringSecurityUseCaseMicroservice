package org.springsecurity.microserviceusecases.prouctservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springsecurity.microserviceusecases.prouctservice.dtos.ProductDto;
import org.springsecurity.microserviceusecases.prouctservice.entities.Product;
import org.springsecurity.microserviceusecases.prouctservice.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/get-all")
    public List<ProductDto> getAllProducts()
    {
        List<Product> products = productService.getProducts();

        return products.stream()
                .map(product -> from(product))
                .collect(Collectors.toList());
    }

    @PutMapping("/product/update")
    public ProductDto changeProductDetails(@RequestBody ProductDto productDto) {
        return from(productService.updateProduct(from(productDto)));
    }

    @PostMapping("/product/create")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return from(productService.createProduct(from(productDto)));
    }

    @DeleteMapping("/product/delete/{productId}")
    public void deleteProduct(@PathVariable long productId)
    {
            productService.deleteProduct(productId);
    }

    Product from(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductPrice(productDto.getProductPrice());
        System.out.println(productDto);
        return product;
    }

    ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setProductDescription(product.getProductDescription());
        productDto.setProductPrice(product.getProductPrice());
        return productDto;
    }
}
