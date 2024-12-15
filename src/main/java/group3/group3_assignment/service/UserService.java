package group3.group3_assignment.service;

import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Recipe;
// import group3.group3_assignment.repository.UserRepo;
// import group3.group3_assignment.repository.RecipeRepo;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addUser(User user);
    List<Recipe> getRecipesByUser(Integer userId);
    Recipe uploadRecipe(Integer userId, Recipe recipe);
}
// public class UserService {
//     @Autowired
//     private UserRepo userRepo; 

//     @Autowired
//     private RecipeRepo recipeRepo;

//     public User addUser(User user) {
//         return userRepo.save(user);
//     }

//     public List<Recipe> getRecipesByUser(Integer userId) {
//         User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//         return user.getRecipes();
//     }

//     public Recipe uploadRecipe(Integer userId, Recipe recipe) {
//         User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//         recipe.setUser(user);
//         return recipeRepo.save(recipe);
//     }
// }

