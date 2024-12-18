package group3.group3_assignment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1)
                .username("john_doe")
                .email("john.doe@example.com")
                .password("securePassword123")
                .build();
    }

    @Test
    public void testAddUser() {
        when(userRepo.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("john_doe", savedUser.getUsername());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testGetUserById_UserFound() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(1);

        assertNotNull(foundUser);
        assertEquals("john_doe", foundUser.getUsername());
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(1));
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    public void testUpdateUser() {
        User updatedDetails = User.builder()
                .username("updated_name")
                .email("updated.email@example.com")
                .password("newPassword123")
                .build();

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(1, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("updated_name", updatedUser.getUsername());
        verify(userRepo, times(1)).findById(1);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepo, times(1)).findById(1);
        verify(userRepo, times(1)).delete(user);
    }
}
