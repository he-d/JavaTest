package at.ac.uibk.estore.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

public interface JwtService {

    String generateToken(UserDetails user);

    boolean isTokenValid(String token, UserDetails userDetails);

    String extractUserName(String token) throws JWTVerificationException;

    Instant extractExpiresAt(String token) throws JWTVerificationException;

    String extractIssuer(String token) throws JWTVerificationException;

    String extractRoles(String token) throws JWTVerificationException;
}
