package at.ac.uibk.estore.service;

import at.ac.uibk.estore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getLatestProducts(Pageable pageable);
}
