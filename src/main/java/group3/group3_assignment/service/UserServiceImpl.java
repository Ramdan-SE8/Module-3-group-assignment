package group3.group3_assignment.service;

// import java.util.ArrayList;
import java.util.List;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.repository.RecipeRepo;
// import group3.group3_assignment.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RecipeRepo recipeRepo;

    // @Autowired
    public UserServiceImpl(UserRepo userRepo, RecipeRepo recipeRepo) {
        this.userRepo = userRepo;
        this.recipeRepo = recipeRepo;
    }

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<Recipe> getRecipesByUser(Integer userId) {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return user.getRecipes();
    }

    @Override
    public Recipe uploadRecipe(Integer userId, Recipe recipe) {
        User user = userRepo.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        recipe.setUser(user);
        return recipeRepo.save(recipe);
    }

    public User getUser(String id) {
        return userRepo.findById(Integer.parseInt(id))
                       .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User updateUser(String id, User user) {
        User existingUser = getUser(id);
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepo.save(existingUser);
    }
}
