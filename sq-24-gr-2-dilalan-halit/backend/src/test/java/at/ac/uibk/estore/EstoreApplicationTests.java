package at.ac.uibk.estore;

import at.ac.uibk.estore.controller.impl.ProductControllerImpl;
import at.ac.uibk.estore.dto.RegistrationRequest;
import at.ac.uibk.estore.model.Address;
import at.ac.uibk.estore.model.User;
import at.ac.uibk.estore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class EstoreApplicationTests {


    private final static PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return (rawPassword.toString().equals(encodedPassword));
        }
    };


    private static ProductRepository productRepository;
    private static ProductControllerImpl productController;

    private static RegistrationRequest testRequest;

    @BeforeEach
    void setup() {
        loadTestRequest();
    }

    private void loadTestRequest() {
        testRequest = new RegistrationRequest();
        testRequest.setUsername("maxMuster");
        testRequest.setPassword("passwd");
        testRequest.setEmail("max.muster@muster.max");
        testRequest.setFirstname("Max");
        testRequest.setLastname("Muster");
        testRequest.setNotification(true);
        testRequest.setStreet("Technikerstraße 21a");
        testRequest.setZip("6020");
        testRequest.setCity("Innsbruck");
        testRequest.setCountry("Austria");
    }


    @Test
    void registerNewUserTest() {
        Address address = new Address(null,
                testRequest.getCountry(),
                testRequest.getCity(),
                testRequest.getStreet(),
                testRequest.getZip());

        User user = new User(testRequest.getUsername(),
                passwordEncoder.encode(testRequest.getPassword()),
                testRequest.getFirstname(),
                testRequest.getLastname(),
                testRequest.getEmail(),
                testRequest.getNotification(),
                address);

        Assertions.assertEquals("maxMuster", user.getUsername());
        Assertions.assertEquals("passwd", user.getPassword());
        Assertions.assertEquals("max.muster@muster.max", user.getEmail());
        Assertions.assertEquals("Max", user.getFirstname());
        Assertions.assertEquals("Muster", user.getLastname());
        Assertions.assertEquals(true, user.getNotification());
        Assertions.assertEquals("Technikerstraße 21a", user.getAddress().getStreet());
        Assertions.assertEquals("6020", user.getAddress().getZip());
        Assertions.assertEquals("Innsbruck", user.getAddress().getCity());
        Assertions.assertEquals("Austria", user.getAddress().getCountry());
    }
}
