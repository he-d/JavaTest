package at.ac.uibk.estore.controller.impl;

import at.ac.uibk.estore.controller.AuthenticationController;
import at.ac.uibk.estore.dto.AuthenticationRequest;
import at.ac.uibk.estore.dto.AuthenticationResponse;
import at.ac.uibk.estore.model.User;
import at.ac.uibk.estore.repository.UserRepository;
import at.ac.uibk.estore.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        System.out.println("Signin request: " + request.getUsername() + " " + request.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                    () -> new IllegalArgumentException("Invalid email or password."));
            String token = jwtService.generateToken(user);

            Cookie cookie = new Cookie("token", token);
            cookie.setSecure(false); // set to true if you want the cookie to be sent over HTTPS only
            cookie.setHttpOnly(true); // protects the cookie from XSS attacks
            cookie.setPath("/");
            cookie.setAttribute("SameSite", "Strict");

            response.addCookie(cookie);

            return ResponseEntity.ok(new AuthenticationResponse(user.getFirstname()));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email or password." + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
