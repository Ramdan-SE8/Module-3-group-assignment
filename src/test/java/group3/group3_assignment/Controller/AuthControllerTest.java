package group3.group3_assignment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import group3.group3_assignment.entity.AuthRequest;
import group3.group3_assignment.entity.User;

import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.service.JwtUtilServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

        @Autowired
        private MockMvc mockMvc; // MockMvc to perform HTTP requests

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private JwtUtilServiceImpl jwtUtillServiceImpl;

        @Autowired
        private UserRepo userRepo;
        private User user;

        @BeforeEach
        public void setUp() {
                userRepo.deleteAll();

                user = User.builder()
                                .username("Sally")
                                .email("sally@gmail.com")
                                .password("password123")
                                .build();

                userRepo.save(user);

        }

        @Test
        public void generateToken() throws Exception {

                // 1) Serialize the user object to JSON
                String userJson = objectMapper.writeValueAsString(user);

                // 2) build a post request to "/users"
                RequestBuilder request = MockMvcRequestBuilders.post("/auth/generateToken")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson);

                // 3) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        public void validateToken() throws Exception {

                Map<String, String> token = jwtUtillServiceImpl.generateToken(user.getUsername(), user.getPassword());
                String tokenValue = JsonPath.read(token, "$.token");
                // 2) build a post request to "/users"
                RequestBuilder request = MockMvcRequestBuilders.post("/auth/validateToken")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + tokenValue);

                // 3) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.username").value("Sally"));

        }

}
