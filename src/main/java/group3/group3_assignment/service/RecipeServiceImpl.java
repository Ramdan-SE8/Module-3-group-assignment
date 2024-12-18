package group3.group3_assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.exception.UserNotAuthorizeException;
import group3.group3_assignment.repository.RecipeRepo;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepo recipeRepo;

    public RecipeServiceImpl(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
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
        // try to get recipe with userid and recipeid. if not found, throw exception.
        Recipe recipeToUpdate = recipeRepo.findByUser_IdAndId(userId, recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));

        // recipe is found
        // throw exception if the user id is not the same as the user id found in
        // recipes
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
        // try to get recipe with userid and recipeid. if not found, throw exception.
        Recipe recipeToDelete = recipeRepo.findByUser_IdAndId(userId, recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));

        // recipe is found
        // throw exception if the user id is not the same as the user id found in
        // recipes
        if (!recipeToDelete.getUser().getId().equals(userId)) {
            throw new UserNotAuthorizeException(userId, "delete");
        }
        // delete the recipe id user id is valid
        recipeRepo.delete(recipeToDelete);
    }
}
