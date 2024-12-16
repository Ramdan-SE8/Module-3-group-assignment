package group3.group3_assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import group3.group3_assignment.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    User createUser(User user);

    User getUser(User user);

    ArrayList<User> getAllUsers();

    User updateUser(User user, User user2);

}
