package at.ac.uibk.estore.controller;

import at.ac.uibk.estore.dto.RegistrationRequest;
import at.ac.uibk.estore.projection.UserProjection;
import org.springframework.http.ResponseEntity;

public interface UserController {
    ResponseEntity<UserProjection> currentUser();

    ResponseEntity<Void> register(RegistrationRequest request);
}
