package ru.mtuci.rbpo_2024_praktika.service;

import ru.mtuci.rbpo_2024_praktika.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> findProductById(Long id);

    Product createProduct(Product product);
    Product updateProduct(Product product);

    void deleteProductById(Long id);

    Product blockProductById(Long id, Boolean blocked);
}