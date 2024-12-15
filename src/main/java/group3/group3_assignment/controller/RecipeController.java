package group3.group3_assignment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.group3_assignment.entity.Favourites;
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
    private FavouritesService favouritesService;  //Added by Ramdan 2024-12-15

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    public RecipeController(RecipeService recipeService, FavouritesService favouritesService) {
        this.recipeService = recipeService;
        this.favouritesService = favouritesService;  //Added by Ramdan 2024-12-15
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

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getOneRecipe(@PathVariable Integer id) {
        Recipe oneRecipe = recipeService.getOneRecipe(id);
        logger.info("Got one recipe with id " + id + ".");
        return new ResponseEntity<>(oneRecipe, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateOneRecipe(@PathVariable Integer id, @Valid @RequestBody Recipe Recipe) {
        Recipe updatedRecipe = recipeService.updateOneRecipe(id, Recipe);
        logger.info("Updated recipe with id " + id + ".");
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOneRecipe(@PathVariable Integer id) {
        recipeService.deleteRecipe(id);
        logger.info("Deleted recipe with id " + id + ".");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //****************************** */ Post one favourite (added by Ramdan 2024-12-15)******************************
    @PostMapping("/me{id}/favourite{recipeId}")
    public ResponseEntity<Favourites> addFavouriteToRecipe(@PathVariable Long id, @PathVariable Integer recipeId,
        @RequestBody Favourites favourites) {
        Favourites newFavourites = favouritesService.addFavourites(id, recipeId, favourites);
        logger.info("User (userId "+id+") added one recipe (recipeId "+recipeId+ ") to favourites list");
        return new ResponseEntity<>(newFavourites, HttpStatus.CREATED);
    }
    //********************************************************************************************************************** */
}

// Recipe createRecipe(Recipe recipe);

// Recipe getOneRecipe(Integer id);

// List<Recipe> getAllRecipes();

// Recipe updateOneRecipe(Integer id, Recipe recipe);

// void deleteRecipe(Integer id);