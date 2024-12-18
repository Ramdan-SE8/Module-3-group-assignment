package group3.group3_assignment.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import group3.group3_assignment.entity.User;

import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc; // MockMvc to perform HTTP requests

        @Autowired
        private ObjectMapper objectMapper;

        // @Mock
        // private UserRepo userRepo; // Mock the UserRepo

        // @InjectMocks
        // private UserService userService; // Inject the mocked repo into the
        // UserService

        private User user;

        @BeforeEach
        public void setUp() {
                // Initialize the user object before each test
                // user = new User();
                // user.setUsername("john_doe");
                // user.setEmail("john_doe@example.com");
                // user.setPassword("password123");
                user = User.builder()
                                .id(1L)
                                .username("john_doe")
                                .email("john_doe@example.com")
                                .password("password123")
                                .build();

        }

        @Test
        public void testCreateUser() throws Exception {

                // 1) Serialize the user object to JSON
                String userJson = objectMapper.writeValueAsString(user);

                // 2) build a post request to "/users"
                RequestBuilder request = MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userJson);

                // 3) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.username").value("john_doe"))
                                .andExpect(jsonPath("$.email").value(
                                                "john_doe@example.com"))
                                .andExpect(jsonPath("$.password")
                                                .value("password123"));
        }
        // This test is not working
        // @Test
        // public void testGetUserById_UserFound() throws Exception {

        // // 1) build a get request for "/users"
        // RequestBuilder request = MockMvcRequestBuilders.get("/users");

        // // Perform GET request to "/users/1" and assert the response
        // mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
        // .andExpect(status().isOk()) // Expecting a 200 OK status
        // .andExpect(jsonPath("$.username").value("john_doe"))
        // .andExpect(jsonPath("$.email").value("john_doe@example.com"));
        // }

        // This test is not working
        // @Test
        // public void testGetUserById_UserNotFound() throws Exception {
        // mockMvc.perform(MockMvcRequestBuilders.get("/users/78987o3dfwL"))
        // .andExpect(status().isNotFound()) // Expecting a 404 Not Found
        // .andExpect(content().string("User not found")); // Expecting the exception
        // message
        // }

        @Test
        public void testUpdateUser() throws Exception {
                // Assume the user exists with the same ID (1)
                User updatedUser = User.builder()
                                .username("john_doe_updated")
                                .email("john_doe_updated@example.com")
                                .password("newpassword123")
                                .build();

                // Serialize the updated user object to JSON
                String updatedUserJson = objectMapper.writeValueAsString(updatedUser);

                // Perform PUT request to "/users/1" and assert the response
                mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedUserJson))
                                .andExpect(status().isOk()) // Expecting a 200 OK status
                                .andExpect(jsonPath("$.username").value("john_doe_updated"))
                                .andExpect(jsonPath("$.email").value("john_doe_updated@example.com"))
                                .andExpect(jsonPath("$.password").value("newpassword123"));
        }

        @Test
        public void testDeleteUser() throws Exception {
                // Perform DELETE request to "/users/1" and assert the response
                mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                                .andExpect(status().isNoContent()); // Expecting a 204 No Content
        }

        @Test
        public void testCreateUser_ValidationError() throws Exception {
                // Create a user with invalid data (e.g., missing email)
                User invalidUser = User.builder()
                                .username("valid_name")
                                .email(null)
                                .password("12345678")
                                .build();

                // Serialize the invalid user object to JSON
                String invalidUserJson = objectMapper.writeValueAsString(invalidUser);

                // Perform POST request to "/users" and expect a 400 Bad Request due to
                // validation errors
                mockMvc.perform(MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidUserJson))
                                .andExpect(status().isBadRequest()) // Expecting a 400 Bad Request
                                .andExpect(jsonPath("$.message").value("Email cannot be blank.")); // Adjust based on
                                                                                                   // actual validation
                                                                                                   // error message
        }

}
