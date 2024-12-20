package group3.group3_assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import group3.group3_assignment.entity.User;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Optional<User> findById(Long id);

    // List<User> findAll();
}
