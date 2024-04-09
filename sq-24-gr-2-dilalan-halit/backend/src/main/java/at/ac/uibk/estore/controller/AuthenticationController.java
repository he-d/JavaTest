package at.ac.uibk.estore.controller;

import at.ac.uibk.estore.dto.AuthenticationRequest;
import at.ac.uibk.estore.dto.AuthenticationResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationController {
    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request, HttpServletResponse response);
}
