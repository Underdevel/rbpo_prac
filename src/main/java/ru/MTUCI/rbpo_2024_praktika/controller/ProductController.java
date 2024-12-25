package ru.MTUCI.rbpo_2024_praktika.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.MTUCI.rbpo_2024_praktika.model.Product;
import ru.MTUCI.rbpo_2024_praktika.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/read")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if(product.getId() != null && productService.findProductById(product.getId()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Product with id: " + product.getId() + " already exist");
        }
        if(product.getBlocked() == null){
            product.setBlocked(false);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(product));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return  productService.findProductById(id).map(productFromDb -> {
            productFromDb.setName(product.getName());
            if(product.getBlocked() != null){
                productFromDb.setBlocked(product.getBlocked());
            }
            return  ResponseEntity.ok(productService.updateProduct(productFromDb));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/block/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockProduct(@PathVariable Long id) {
        return   productService.findProductById(id).map(product -> {
            product.setBlocked(true);
            return  ResponseEntity.ok(productService.updateProduct(product));
        }).orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/unblock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unblockProduct(@PathVariable Long id) {
        return   productService.findProductById(id).map(product -> {
            product.setBlocked(false);
            return  ResponseEntity.ok(productService.updateProduct(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        if (!productService.findProductById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProductById(id);
        return ResponseEntity.ok("Successful");
    }
}