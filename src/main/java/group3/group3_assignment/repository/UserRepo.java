package group3.group3_assignment.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import group3.group3_assignment.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    User createUser(User user);

    User getUser(User user);

    ArrayList<User> getAllUsers();

    User updateUser(User user, User user2);

    Object findById(Long id);
 }
