package at.ac.uibk.estore.controller;

import at.ac.uibk.estore.model.Product;
import at.ac.uibk.estore.projection.ProductProjection;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ProductController {
    ResponseEntity<Collection<ProductProjection>> getProducts(String category);

    ResponseEntity<Product> getProductById(int productId);
}
