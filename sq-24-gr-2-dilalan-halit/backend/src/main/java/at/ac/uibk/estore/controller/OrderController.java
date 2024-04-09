package at.ac.uibk.estore.controller;

import at.ac.uibk.estore.dto.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderController {
    ResponseEntity<Void> submit(OrderRequest request);
}
