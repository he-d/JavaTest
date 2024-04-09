package at.ac.uibk.estore.repository;

import at.ac.uibk.estore.model.Product;
import at.ac.uibk.estore.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("SELECT p.id as id, p.name as name, p.price as price, p.stock as stock, p.category as category, " +
            "p.image as image FROM Product p")
    Page<ProductProjection> getTopProducts(Pageable pageable);

    @Query("SELECT p.id as id, p.name as name, p.price as price, p.stock as stock, p.category as category, " +
            "p.image as image FROM Product p WHERE p.category = ?1")
    Page<ProductProjection> getProductsByCategory(String category, Pageable pageable);
}