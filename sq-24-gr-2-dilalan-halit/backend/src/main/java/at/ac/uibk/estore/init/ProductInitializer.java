package at.ac.uibk.estore.init;

import at.ac.uibk.estore.model.Product;
import at.ac.uibk.estore.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ProductInitializer {

    private final Logger logger = LoggerFactory.getLogger(ProductInitializer.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void initializeData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json");

        try {
            List<Product> products = objectMapper.readValue(inputStream, typeReference);
            productRepository.saveAll(products);
            logger.info("Products initialized successfully!");
        } catch (IOException e) {
            System.err.println("Error initializing products: " + e.getMessage());
        }
    }
}
