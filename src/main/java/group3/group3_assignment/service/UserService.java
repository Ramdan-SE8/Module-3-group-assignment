package group3.group3_assignment.service;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Recipe;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<Recipe> getRecipesByUser(Integer userId);
    Recipe uploadRecipe(Integer userId, Recipe recipe);
    User getUser(Integer id);  // Updated from String to Integer
    User updateUser(Integer id, User user);  // Updated from String to Integer
    void deleteUser(Integer id);
    List<User> getAllUsers();
}