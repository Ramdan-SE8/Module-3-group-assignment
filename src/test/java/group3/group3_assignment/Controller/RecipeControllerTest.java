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

import com.fasterxml.jackson.databind.ObjectMapper;

import group3.group3_assignment.entity.Recipe;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

//integration test
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private ObjectMapper objectMapper;

        private Recipe recipe;

        @BeforeEach
        public void setUp() {
                recipe = Recipe.builder()
                                .title("Stir Fried Noodles")
                                .imgSrc("https://www.seriouseats.com/thmb/KOV3OvnLeh6RW64lEnRixbRxOq4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-QiAi-stir-fried-lo-mein-noodles-pork-vegetables-recipe-hero-a55a4baa9f22449fbe036142f1047430.jpg")
                                .description("A quick and tasty stir-fried noodle recipe packed with veggies and savory sauce.")
                                .ingredients(Arrays.asList("Egg noodles",
                                                "Soy sauce"))
                                .steps(Arrays.asList("Cook the egg noodles according to package instructions.",
                                                "Stir-fry garlic, carrots, and bell peppers in sesame oil."))
                                .build();
        }

        @Test
        public void testCreateRecipe() throws Exception {

                // 1) Serialize the recipe object to JSON
                String recipeJson = objectMapper.writeValueAsString(recipe);

                // 2) build a post request to "/recipe"
                RequestBuilder request = MockMvcRequestBuilders.post("/recipe")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(recipeJson);

                // 3) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.title").value("Stir Fried Noodles"))
                                .andExpect(jsonPath("$.imgSrc").value(
                                                "https://www.seriouseats.com/thmb/KOV3OvnLeh6RW64lEnRixbRxOq4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-QiAi-stir-fried-lo-mein-noodles-pork-vegetables-recipe-hero-a55a4baa9f22449fbe036142f1047430.jpg"))
                                .andExpect(jsonPath("$.description")
                                                .value("A quick and tasty stir-fried noodle recipe packed with veggies and savory sauce."))
                                .andExpect(jsonPath("$.ingredients").isArray())
                                .andExpect(jsonPath("$.ingredients[0]").value("Egg noodles"))
                                .andExpect(jsonPath("$.ingredients[1]").value("Soy sauce"))
                                .andExpect(jsonPath("$.steps").isArray())
                                .andExpect(jsonPath("$.steps[0]")
                                                .value("Cook the egg noodles according to package instructions."))
                                .andExpect(jsonPath("$.steps[1]")
                                                .value("Stir-fry garlic, carrots, and bell peppers in sesame oil."));
        }

        @Test
        public void testGetAllRecipes() throws Exception {
                // 1) build a get request for "/recipe"
                RequestBuilder request = MockMvcRequestBuilders.get("/recipe");
                // 2) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.size()").value(5));
        }

        @Test
        public void testGetOneRecipe() throws Exception {
                // 1) build a request to "/{id}"
                RequestBuilder request = MockMvcRequestBuilders.get("/recipe/2");
                // 2) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(2));
        }

        @Test
        public void testUpdateOneRecipe() throws Exception {

                // 1) Serialize the recipe object to JSON
                String recipeJson = objectMapper.writeValueAsString(recipe);

                // 2) build a request to "/{id}"
                RequestBuilder request = MockMvcRequestBuilders.put("/recipe/3")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(recipeJson);
                // 3) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.title").value("Stir Fried Noodles"))
                                .andExpect(jsonPath("$.imgSrc").value(
                                                "https://www.seriouseats.com/thmb/KOV3OvnLeh6RW64lEnRixbRxOq4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-QiAi-stir-fried-lo-mein-noodles-pork-vegetables-recipe-hero-a55a4baa9f22449fbe036142f1047430.jpg"))
                                .andExpect(jsonPath("$.description")
                                                .value("A quick and tasty stir-fried noodle recipe packed with veggies and savory sauce."))
                                .andExpect(jsonPath("$.ingredients").isArray())
                                .andExpect(jsonPath("$.ingredients[0]").value("Egg noodles"))
                                .andExpect(jsonPath("$.ingredients[1]").value("Soy sauce"))
                                .andExpect(jsonPath("$.steps").isArray())
                                .andExpect(jsonPath("$.steps[0]")
                                                .value("Cook the egg noodles according to package instructions."))
                                .andExpect(jsonPath("$.steps[1]")
                                                .value("Stir-fry garlic, carrots, and bell peppers in sesame oil."));
        }

        @Test
        public void testDeleteOneRecipe() throws Exception {
                // 1) build request to "/{id}"
                RequestBuilder request = MockMvcRequestBuilders.delete("/recipe/1");
                // 2) perform the request and get a response and assert
                mockMvc.perform(request).andExpect(status().isNoContent());
        }
}
