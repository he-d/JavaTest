package at.ac.uibk.estore.init;

import at.ac.uibk.estore.model.User;
import at.ac.uibk.estore.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class UserInitializer {

    private final Logger logger = LoggerFactory.getLogger(UserInitializer.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initializeData() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json");

        try {
            List<User> users = objectMapper.readValue(inputStream, typeReference);
            users.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
            userRepository.saveAll(users);
            logger.info("Users initialized successfully!");
        } catch (IOException e) {
            System.err.println("Error initializing users: " + e.getMessage());
        }
    }
}
