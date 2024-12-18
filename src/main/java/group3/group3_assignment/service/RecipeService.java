package group3.group3_assignment.service;

import java.util.List;

import group3.group3_assignment.entity.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);

    Recipe getOneRecipe(Integer userId);

    List<Recipe> getAllRecipes();

    Recipe updateOneRecipe(Integer recipeId, Recipe recipe, Long userId);

    void deleteRecipe(Integer recipeId, Long userId);

}
