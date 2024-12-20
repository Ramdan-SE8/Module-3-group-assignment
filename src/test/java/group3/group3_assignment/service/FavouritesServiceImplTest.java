package group3.group3_assignment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.repository.FavouritesRepository;

// unit test for service
@ExtendWith(MockitoExtension.class)
public class FavouritesServiceImplTest {

    // 1. Create a mock repository
    @Mock
    public FavouritesRepository favouritesRepository;

    // 2. inject the mock repository into service implementation class
    @InjectMocks
    FavouritesServiceImpl favouritesServiceImpl;

    private Favourites favourites;
    private User user;
    private Recipe recipe;

    // initialize favourites object before each test
    @BeforeEach
    public void setup() {
        String dateToParse = "2024-05-15";
        LocalDate parsedDate = LocalDate.parse(dateToParse);

        user = User.builder().id(1L).build();

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

        favourites = Favourites.builder()
                .id(1L)
                .remarks("faved")
                .favouritesDate(parsedDate)
                .recipe(recipe)
                .user(user)
                .build();

    }

    // 3. create the test
    @Test
    public void testaddFavourites() {
        
        // Mock the save method of the favourites repository
        when(favouritesRepository.save(favourites)).thenReturn(favourites);

        // 3b. call the method to be tested
        Favourites addedFavourites = favouritesServiceImpl.addFavourites(1L, 1, favourites);

        // 3c. assert the results
        assertEquals(favourites, addedFavourites, "Favourites should be the same as added favourites");

        // 3d. verify that the the method is only calling the favouritesRepository and saving 1
        // time
        verify(favouritesRepository, times(1)).save(favourites);
    }

    @Test
    public void getFavouritesByUserId() {
        // create a list of favourites to simulate a list of all the recipes
        List<Favourites> listFavourites = Arrays.asList(favourites);

        // mocking favouritesRepository behaviour by finding all recipes and returning the list of
        // recipes
        when(favouritesRepository.findAllByUserId(1L)).thenReturn(listFavourites);

        // calling the getFavouritesByUserId method and returning the favourites
        List<Favourites> allFavourites = favouritesServiceImpl.getFavouritesByUserId(1L);
        assertEquals(listFavourites, allFavourites);

    }

    @Test
    public void testdeleteFavourites() {

        // mocking the favouritesRepository by finding the recipeId and returning an
        // optional
        when(favouritesRepository.findByUserIdAndRecipeId(1L, 1)).thenReturn(null);

        // call the deleteFavourites method
        favouritesServiceImpl.deleteFavourites(1L, 1);

        // verify the delete method is called 1 time with correct id
        verify(favouritesRepository, times(1)).deleteByUserIdAndRecipeId(1L, 1);
    }

    /*
     * 
     * 
     * @Test
     * public void deleteFavourites() {
     * Integer recipeId = 1;
     * Long userId = 1L;
     * 
     * // mocking the favouritesRepository by finding the recipeId and returning an
     * optional
     * when(favouritesRepository.deleteByUserIdAndRecipeId(userId,
     * recipeId)).thenReturn(null);
     * 
     * // call the deleteRecipe method
     * Favourites newFavourites =
     * favouritesServiceImpl.deleteByUserIdAndRecipeId(userId, recipeId);
     * 
     * // Compare the actual result with the expected result
     * assertEquals(favourites, newFavourites,
     * "The saved customer should be the same as the new customer");
     * 
     * // verify the delete method is called 1 time with correct id
     * verify(favouritesRepository, times(1)).deleteByUserIdAndRecipeId(favourites);
     * }
     */

}