package com.example;



import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    void saveProduct(Product product);

    void deleteProduct(Long id);
}
