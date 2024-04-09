package at.ac.uibk.estore.controller.impl;

import at.ac.uibk.estore.controller.UserController;
import at.ac.uibk.estore.dto.RegistrationRequest;
import at.ac.uibk.estore.model.Address;
import at.ac.uibk.estore.model.User;
import at.ac.uibk.estore.projection.UserProjection;
import at.ac.uibk.estore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @GetMapping("current")
    public ResponseEntity<UserProjection> currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        try {
            UserProjection user = userRepository.getUserInfo(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping("register")
    public ResponseEntity<Void> register(@RequestBody RegistrationRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setZip(request.getZip());
        address.setCountry(request.getCountry());

        User user = new User(request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getNotification(),
                new Address(null,
                        request.getCountry(),
                        request.getCity(),
                        request.getStreet(),
                        request.getZip()));

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
