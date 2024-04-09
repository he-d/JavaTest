package at.ac.uibk.estore.service.impl;

import at.ac.uibk.estore.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Setter
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${token.signing.key}")
    private String secret;

    private Integer expirationTimeInMinutes = 10;

    private Algorithm algorithm;

    private JWTVerifier verifier;

    @PostConstruct
    public void init(){
        this.algorithm = Algorithm.HMAC256(this.secret);
        this.verifier = JWT.require(this.algorithm).build();
    }

    @Override
    public String generateToken(UserDetails user) {
        Instant expiration = generateExpirationTimeIn();  // expires in 10 min
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiration)
                .withIssuer("EStore-API")
                .withClaim("roles", "USER")
                .sign(this.algorithm);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            this.verifier.verify(token);
            return this.extractUserName(token).equals(userDetails.getUsername());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractUserName(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = this.verifier.verify(token);
        return decodedJWT.getSubject();
    }

    @Override
    public Instant extractExpiresAt(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = this.verifier.verify(token);
        return decodedJWT.getExpiresAtAsInstant();
    }

    @Override
    public String extractIssuer(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = this.verifier.verify(token);
        return decodedJWT.getIssuer();
    }

    @Override
    public String extractRoles(String token) throws JWTVerificationException {
        DecodedJWT decodedJWT = this.verifier.verify(token);
        return decodedJWT.getClaim("roles").asString();
    }

    private Instant generateExpirationTimeIn() {
        return LocalDateTime.now().plusMinutes(this.expirationTimeInMinutes).atZone(ZoneId.systemDefault()).toInstant();
    }
}
