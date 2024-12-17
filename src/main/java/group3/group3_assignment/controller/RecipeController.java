package group3.group3_assignment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.service.FavouritesService;
import group3.group3_assignment.service.RecipeService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    public RecipeController(RecipeService recipeService, FavouritesService favouritesService) {
        this.recipeService = recipeService;
    }

    @PostMapping("")
    private ResponseEntity<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.createRecipe(recipe);
        logger.info("Created new Recipe");
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = recipeService.getAllRecipes();
        logger.info("Got all recipes");
        return new ResponseEntity<>(allRecipes, HttpStatus.OK);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getOneRecipe(@PathVariable Integer recipeId) {
        Recipe oneRecipe = recipeService.getOneRecipe(recipeId);
        logger.info("Got one recipe with id " + recipeId + ".");
        return new ResponseEntity<>(oneRecipe, HttpStatus.OK);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Recipe> updateOneRecipe(@PathVariable Integer recipeId, @RequestParam Integer userId,
            @Valid @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.updateOneRecipe(recipeId, recipe, userId);
        logger.info("User with id " + userId + "Updated recipe with id " + recipeId + ".");
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<HttpStatus> deleteOneRecipe(@PathVariable Integer recipeId, @RequestParam Integer userId) {
        recipeService.deleteRecipe(recipeId, userId);
        logger.info("Deleted recipe with id " + recipeId + ".");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}