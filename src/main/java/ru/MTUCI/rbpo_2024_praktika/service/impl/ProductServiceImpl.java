package ru.MTUCI.rbpo_2024_praktika.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.MTUCI.rbpo_2024_praktika.model.Product;
import ru.MTUCI.rbpo_2024_praktika.repository.ProductRepository;
import ru.MTUCI.rbpo_2024_praktika.service.ProductService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getId())){
            throw new IllegalStateException("Product not found: " + product.getId());
        }
        return productRepository.save(product);
    }


    @Override
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)){
            throw new IllegalStateException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product blockProductById(Long id, Boolean blocked) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new IllegalStateException("Product not found: " + id));
        product.setBlocked(blocked);
        return productRepository.save(product);
    }
}