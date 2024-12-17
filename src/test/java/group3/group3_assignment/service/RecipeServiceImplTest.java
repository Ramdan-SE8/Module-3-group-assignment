package group3.group3_assignment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.repository.RecipeRepo;

// unit test for service
@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    // 1. Create a mock repository
    @Mock
    public RecipeRepo recipeRepo;

    // 2. inject the mock repository into service implementation class
    @InjectMocks
    RecipeServiceImpl recipeServiceImpl;

    private Recipe recipe;
    private User user;

    // initialize recipe object before each test
    @BeforeEach
    public void setup() {
        user = User.builder().id(1).build();
        recipe = Recipe.builder().title("Stir Fried Noodles")
                .id(1)
                .user(user)
                .imgSrc("https://www.seriouseats.com/thmb/KOV3OvnLeh6RW64lEnRixbRxOq4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-QiAi-stir-fried-lo-mein-noodles-pork-vegetables-recipe-hero-a55a4baa9f22449fbe036142f1047430.jpg")
                .description("A quick and tasty stir-fried noodle recipe packed with veggies and savory sauce.")
                .ingredients(Arrays.asList("Egg noodles",
                        "Soy sauce",
                        "Garlic cloves",
                        "Carrots",
                        "Bell peppers",
                        "Sesame oil",
                        "Green onions"))
                .steps(Arrays.asList("Cook the egg noodles according to package instructions.",
                        "Stir-fry garlic, carrots, and bell peppers in sesame oil.",
                        "Add cooked noodles and soy sauce, tossing until combined.",
                        "Garnish with green onions and serve."))
                .build();

    }

    // 3. create the test
    @Test
    public void testCreateRecipe() {
        // 3a. mocking recipeRepo behaviour by saving the recipe into the mock
        // repository
        when(recipeRepo.save(recipe)).thenReturn(recipe);

        // 3b. call the method to be tested
        Recipe createdRecipe = recipeServiceImpl.createRecipe(recipe);

        // 3c. assert the results
        assertEquals(recipe, createdRecipe, "Recipe should be the same as create recipe");

        // 3d. verify that the the method is only calling the recipe repo and saving 1
        // time
        verify(recipeRepo, times(1)).save(createdRecipe);
    }

    @Test
    public void testCreateRecipeDatabaseConnectionError() {
        // 3a. mocking recipeRepo behaviour by saving the recipe into mock repository
        // but throws an error
        when(recipeRepo.save(recipe)).thenThrow(new RuntimeException("Database connection error"));

        // 3b. create a RunTimeException object using the results of createRecipe
        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeServiceImpl.createRecipe(recipe));

        // 3c. asserts the results by getting message of exception
        assertEquals("Database connection error", exception.getMessage());
    }

    @Test
    public void testGetOneRecipe() {
        // determine the id of recipe to find
        Integer recipeId = 1;
        // mocking recipeRepo behaviour by finding the recipe base on the recipe id and
        // return optional
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.of(recipe));
        // calling getOneRecipe method and returning the recipe object
        Recipe foundRecipe = recipeServiceImpl.getOneRecipe(recipeId);
        assertEquals(recipe, foundRecipe);
    }

    @Test
    public void testGetOneRecipeNotFound() {
        Integer recipeId = 2;

        // mock the repo behaviour by finding the id
        when(recipeRepo.findById(recipeId)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> recipeServiceImpl.getOneRecipe(recipeId));
    }

    @Test
    public void testGetAllRecipes() {
        // create a list of recipes to simulate a list of all the recipes
        List<Recipe> recipes = Arrays.asList(recipe);

        // mocking recipeRepo behaviour by finding all recipes and returning the list of
        // recipes
        when(recipeRepo.findAll()).thenReturn(recipes);

        // calling the getAllRecipes method and returning the recipes
        List<Recipe> allRecipes = recipeServiceImpl.getAllRecipes();
        assertEquals(recipes, allRecipes);
    }

    @Test
    public void testUpdateOneRecipe() {
        Integer recipeId = 1;
        Integer userId = 1;

        // mocking the recipeRepo by finding the recipeId and returning an optional
        when(recipeRepo.findByUser_IdAndId(userId, recipeId)).thenReturn(Optional.of(recipe));

        // creating a updated recipe
        Recipe recipeToUpdate = Recipe.builder()
                .id(userId)
                .user(user)
                .title("Dijon Mustard Salmon")
                .imgSrc("https://getfish.com.au/cdn/shop/articles/Step_4_-_crispy_salmon.png?v=1715832861")
                .description("A simple yet flavorful Dijon mustard salmon baked to perfection.")
                .ingredients(Arrays.asList("Salmon fillets",
                        "Dijon mustard",
                        "Honey",
                        "Garlic cloves",
                        "Lemon juice",
                        "Salt",
                        "Pepper"))
                .steps(Arrays.asList("Preheat oven to 200°C.",
                        "Whisk Dijon mustard, honey, garlic, and lemon juice together.",
                        "Brush the salmon fillets with the mustard mixture and season with salt and pepper.",
                        "Bake for 12-15 minutes until salmon is cooked through."))
                .build();

        // Mock saving the repo then return the updated recipe (this will be called when
        // the updateOne recipe is called)
        when(recipeRepo.save(recipe)).thenReturn(recipeToUpdate);

        // call the updateOneRecipe method
        Recipe updatedRecipe = recipeServiceImpl.updateOneRecipe(recipeId, recipeToUpdate, userId);

        // asserts the result
        assertEquals(recipeToUpdate, updatedRecipe);

        // verify that the repository only save 1 time
        verify(recipeRepo, times(1)).save(recipe);
    }

    @Test
    public void testUpdateOneRecipeNotFound() {
        Integer recipeId = 2;
        Integer userId = 2;

        // mock the repo behaviour by finding the id
        when(recipeRepo.findByUser_IdAndId(userId, recipeId)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> recipeServiceImpl.updateOneRecipe(recipeId, recipe, userId));
    }

    @Test
    public void testDeleteRecipe() {
        Integer recipeId = 1;
        Integer userId = 1;

        // mocking the recipeRepo by finding the recipeId and returning an optional
        when(recipeRepo.findByUser_IdAndId(userId, recipeId)).thenReturn(Optional.of(recipe));

        // call the deleteRecipe method
        recipeServiceImpl.deleteRecipe(recipeId, userId);

        // verify the delete method is called 1 time with correct id
        verify(recipeRepo, times(1)).delete(recipe);
    }

    @Test
    public void testDeleteRecipeNotFound() {
        Integer recipeId = 2;
        Integer userId = 2;

        // mock the repo behaviour by finding the id
        when(recipeRepo.findByUser_IdAndId(userId, recipeId)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> recipeServiceImpl.deleteRecipe(recipeId, userId));
    }
}