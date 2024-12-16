package group3.group3_assignment.service;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Recipe;

import java.util.List;

public interface UserService {
    User addUser(User user);

    User getUser(Integer id);

    List<User> getAllUsers();

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);

    Recipe addRecipeToUser(Integer id, Recipe recipe);

    List<Recipe> getRecipesByUser(Integer userId);

    // Recipe uploadRecipe(Integer userId, Recipe recipe);

}
// public class UserService {
// @Autowired
// private UserRepo userRepo;

// @Autowired
// private RecipeRepo recipeRepo;

// public User addUser(User user) {
// return userRepo.save(user);
// }

// public List<Recipe> getRecipesByUser(Integer userId) {
// User user = userRepo.findById(userId).orElseThrow(() -> new
// RuntimeException("User not found"));
// return user.getRecipes();
// }

// public Recipe uploadRecipe(Integer userId, Recipe recipe) {
// User user = userRepo.findById(userId).orElseThrow(() -> new
// RuntimeException("User not found"));
// recipe.setUser(user);
// return recipeRepo.save(recipe);
// }
// }
