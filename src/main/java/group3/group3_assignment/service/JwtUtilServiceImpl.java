package group3.group3_assignment.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.TokenNotGeneratedException;
import group3.group3_assignment.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtilServiceImpl implements JwtUtillService {
    UserRepo userRepo;

    public JwtUtilServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private final String secretKey = "2c5a9f9c8c4d6eaf4f9a9c5d2f317c8d1b8f7e7d69f8b18f01b06f3a828c09a2";
    private final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public Map<String, String> generateToken(String username, String password) {
        Optional<User> userToCheck = userRepo.findByUsername(username);
        if (userToCheck.isPresent()) {
            User user = userToCheck.get();
            if (user.getPassword().equals(password)) {
                Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
                Date currentDate = new Date(System.currentTimeMillis());
                String jws = Jwts.builder()
                        .subject(username)
                        .expiration(expirationDate)
                        .issuedAt(currentDate)
                        .signWith(key)
                        .compact();

                Map<String, String> response = new HashMap<>();
                response.put("token", jws);
                return response;
            } else {
                throw new TokenNotGeneratedException("Invalid Password");
            }
        } else {
            throw new TokenNotGeneratedException("Invalid Username");
        }
    }

    public Map<String, String> validateToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        String username = claimsJws.getPayload().getSubject();
        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        return response;
    }

}
