package group3.group3_assignment.service;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.repository.RecipeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RecipeRepo recipeRepo;

    public UserServiceImpl(UserRepo userRepo, RecipeRepo recipeRepo) {
        this.userRepo = userRepo;
        this.recipeRepo = recipeRepo;
    }

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    // @Override
    // public List<Recipe> getRecipesByUser(Integer userId) {
    //     User user = userRepo.findById(userId)
    //             .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    //     return user.getRecipes();
    // }

    @Override
    public Recipe uploadRecipe(Integer userId, Recipe recipe) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        // recipe.setUser(user);  // Uncomment this line if User-Recipe relationship is implemented
        return recipeRepo.save(recipe);
    }

    @Override
    public User getUser(Integer id) {  // Updated from String to Integer
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public User updateUser(Integer id, User user) {  // Updated from String to Integer
        User existingUser = getUser(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepo.save(existingUser);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        userRepo.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}
