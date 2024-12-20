package group3.group3_assignment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jayway.jsonpath.JsonPath;

import group3.group3_assignment.repository.UserRepo;
import io.jsonwebtoken.JwtException;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.TokenNotGeneratedException;

@ExtendWith(MockitoExtension.class)
public class JwtUtillServiceTest {

    // create mock repo
    @Mock
    public UserRepo userRepo;

    // inject mock repo into service implemenation layer

    @InjectMocks
    JwtUtilServiceImpl JwtUtilServiceImpl;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder().username("john").email("abc@gmail.com").password("123ABCabc!@#").build();

    }

    @Test
    public void testGenerateToken() {
        // mock userRepo behavoid to return the username and password when called
        when(userRepo.findByUsername("john")).thenReturn(Optional.of(user));
        Map<String, String> token = JwtUtilServiceImpl.generateToken("john", "123ABCabc!@#");
        assertNotNull(token);
    }

    @Test
    public void testGenerateTokenFail() {
        // mock userRepo behavoid to return the username and password when called
        when(userRepo.findByUsername("john")).thenReturn(Optional.empty());
        assertThrows(TokenNotGeneratedException.class, () -> {
            JwtUtilServiceImpl.generateToken("john", "!@#");
        });
    }

    @Test
    public void testValidateToken() {
        when(userRepo.findByUsername("john")).thenReturn(Optional.of(user));
        Map<String, String> token = JwtUtilServiceImpl.generateToken("john", "123ABCabc!@#");
        String tokenValue = JsonPath.read(token, "$.token");
        Map<String, String> username = JwtUtilServiceImpl.validateToken("Bearer " + tokenValue);
        String usernameValue = JsonPath.read(username, "$.username");
        assertEquals("john", usernameValue);
    }

    @Test
    public void testValidateTokenWithInvalidToken() {
        // Use an invalid token, e.g., a malformed token (no "Bearer " prefix)
        String invalidToken = "invalidTokenString";

        // Validate the token and expect an exception to be thrown
        assertThrows(JwtException.class, () -> {
            JwtUtilServiceImpl.validateToken("Bearer " + invalidToken);
        });
    }

}
