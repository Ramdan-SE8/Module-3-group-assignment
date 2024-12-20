package group3.group3_assignment.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;

//integration test
@SpringBootTest
@AutoConfigureMockMvc
public class FavouritesControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;

        private Favourites favourites;
        private Recipe recipe;
        private User user;

        @BeforeEach
        public void setUp() {

                String dateToParse = "2024-05-15";
                LocalDate parsedDate = LocalDate.parse(dateToParse);

                user = User.builder().email("abc@gmail.com").username("John")
                                .password("123ABCabc!@#").build();

                recipe = Recipe.builder()
                                .user(user)
                                .title("Stir Fried Noodles")
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
                                .remarks("faved")
                                .favouritesDate(parsedDate)
                                .recipe(recipe)
                                .user(user)
                                .build();
        }

        @Test
        public void addFavouriteToRecipe() throws Exception {

                // 1) Serialize the favourites object to JSON
                String favouritesJson = objectMapper.writeValueAsString(favourites);

                // 2) build a post request to "favourites/{userId}/{recipeId}"
                RequestBuilder request = MockMvcRequestBuilders.post("favourites/{userId}/{recipeId}")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(favouritesJson);

                // 3) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.remarks").value("faved"))
                                .andExpect(jsonPath("$.favouritesDate").value("2024-12-19"))
                                .andExpect(jsonPath("$.recipe").value("recipe"))
                                .andExpect(jsonPath("$.user").value("user"));
        }

        @Test
        public void deleteOneFavourite() throws Exception {
                // 1) build request to "favourites/{userId}/{recipeId}"
                RequestBuilder request = MockMvcRequestBuilders.delete("favourites/{userId}/{recipeId}");
                // 2) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isNoContent());
        }

        @Test
        public void getAllFavourites() throws Exception {
                // 1) build a get request for "favourites/{userId}"
                RequestBuilder request = MockMvcRequestBuilders.get("favourites/{userId}");
                // 2) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.remarks()").value("faved"))
                                .andExpect(jsonPath("$.favouritesDate").value("2024-12-19"))
                                .andExpect(jsonPath("$.recipe").value("recipe"))
                                .andExpect(jsonPath("$.user").value("user"));
        }
}
