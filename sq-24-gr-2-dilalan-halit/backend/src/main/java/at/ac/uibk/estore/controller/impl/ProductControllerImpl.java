package at.ac.uibk.estore.controller.impl;

import at.ac.uibk.estore.controller.ProductController;
import at.ac.uibk.estore.model.Product;
import at.ac.uibk.estore.projection.ProductProjection;
import at.ac.uibk.estore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<Collection<ProductProjection>> getProducts(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            Page<ProductProjection> productsByCategory = productRepository.getProductsByCategory(category, PageRequest.of(0, 9));
            return ResponseEntity.ok(productsByCategory.getContent());
        } else {
            Page<ProductProjection> topProducts = productRepository.getTopProducts(PageRequest.of(0, 9));
            return ResponseEntity.ok(topProducts.getContent());
        }
    }

    @Override
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        return productRepository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
