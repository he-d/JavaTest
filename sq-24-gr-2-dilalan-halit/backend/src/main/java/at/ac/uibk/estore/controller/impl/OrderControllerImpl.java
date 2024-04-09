package at.ac.uibk.estore.controller.impl;

import at.ac.uibk.estore.controller.OrderController;
import at.ac.uibk.estore.dto.OrderRequest;
import at.ac.uibk.estore.model.*;
import at.ac.uibk.estore.repository.OrderRepository;
import at.ac.uibk.estore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/submit_order")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Override
    @PostMapping
    public ResponseEntity<Void> submit(@RequestBody OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Address address = request.getAddress();
        User user;

        if (request.getProducts().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            if (address == null) {
                user = userRepository.findByUsername(username).orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
                address = user.getAddress();
            } else {
                user = new User();
                user.setUsername(username);
            }

            Order order = new Order(
                    null,
                    user,
                    address,
                    LocalDateTime.now(),
                    OrderStatus.PENDING,
                    request.getProducts().entrySet().stream()
                            .collect(Collectors.toMap(
                                    entry -> {
                                        Product product = new Product();
                                        product.setId(entry.getKey());
                                        return product;
                                    },
                                    Map.Entry::getValue
                            ))
            );

            orderRepository.save(order);
            return ResponseEntity.ok().build();

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }
}
