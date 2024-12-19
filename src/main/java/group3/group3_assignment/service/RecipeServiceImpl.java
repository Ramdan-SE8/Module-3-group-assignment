package group3.group3_assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.exception.UserNotAuthorizeException;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.RecipeRepo;
import group3.group3_assignment.repository.UserRepo;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepo recipeRepo;
    private UserRepo userRepo;

    public RecipeServiceImpl(RecipeRepo recipeRepo, UserRepo userRepo) {
        this.recipeRepo = recipeRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Recipe createRecipeToUser(Long userId, Recipe recipe) {
        User selectedUser = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " is not found"));
        recipe.setUser(selectedUser);
        return recipeRepo.save(recipe);
    }

    @Override
    public Recipe getOneRecipe(Integer id) {
        return recipeRepo.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    @Override
    public Recipe updateOneRecipe(Integer recipeId, Recipe recipe, Long userId) {
        // try to get recipe. if not found, throw exception.
        Recipe recipeToUpdate = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));

        // check if found recipe belongs to the user who created it. if not, throw
        // exception. so only the user who created it can edit.
        if (!recipeToUpdate.getUser().getId().equals(userId)) {
            throw new UserNotAuthorizeException(userId, "edit");
        }

        // if user id is the same, contune to update the recipe
        recipeToUpdate.setDescription(recipe.getDescription());
        recipeToUpdate.setIngredients(recipe.getIngredients());
        recipeToUpdate.setSteps(recipe.getSteps());
        recipeToUpdate.setTitle(recipe.getTitle());
        recipeToUpdate.setImgSrc(recipe.getImgSrc());
        return recipeRepo.save(recipeToUpdate);
    }

    @Override
    public void deleteRecipe(Integer recipeId, Long userId) {
        // try to get recipe if not found, throw exception.
        Recipe recipeToDelete = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
        // check if found recipe belongs to the user who created it. if not, throw
        // exception. so only the user who created it can delete.
        if (!recipeToDelete.getUser().getId().equals(userId)) {
            throw new UserNotAuthorizeException(userId, "delete");
        }
        // delete the recipe id user id is valid
        recipeRepo.delete(recipeToDelete);
    }
}
