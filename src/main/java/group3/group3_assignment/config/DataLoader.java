package group3.group3_assignment.config;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.repository.RecipeRepo;
import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    private RecipeRepo recipeRepo;

    public DataLoader(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @PostConstruct
    public void loadData() {
        recipeRepo.deleteAll();
        recipeRepo.save(Recipe.builder()
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
                .build());

        recipeRepo.save(Recipe.builder()
                .title("Seared Eggplant")
                .imgSrc("https://www.sweetashoney.co/wp-content/uploads/Sauteed-Eggplant-2.jpg")
                .description("This seared eggplant dish is perfect for a light and flavorful vegetarian meal.")
                .ingredients(Arrays.asList("Eggplant",
                        "Olive oil",
                        "Garlic cloves",
                        "Lemon juice",
                        "Salt",
                        "Pepper",
                        "Parsley"))
                .steps(Arrays.asList("Slice eggplant and season with salt and pepper.",
                        "Sear in olive oil until golden and soft.",
                        "Drizzle with lemon juice and garnish with parsley before serving."))
                .build());

        recipeRepo.save(Recipe.builder()
                .title("Savory Carrot Muffins")
                .imgSrc("https://images.contentstack.io/v3/assets/blt8a393bb3b76c0ede/blt653e46ad897ffac9/65482c54d2a103040ad8d9ef/savoury-breakfast-muffins-recipe-header.jpg?height=675.0&width=1200.0&crop=1200.0%2C675.0%2Cx0.0%2Cy45.0&format=pjpg&auto=webp")
                .description("Delicious savory muffins made with carrots and a blend of spices.")
                .ingredients(Arrays.asList("Grated carrots",
                        "Whole wheat flour",
                        "Eggs",
                        "Milk",
                        "Baking powder",
                        "Cheddar cheese",
                        "Paprika"))
                .steps(Arrays.asList("Preheat oven to 180°C and line a muffin tray.",
                        "Mix grated carrots, flour, eggs, milk, baking powder, and paprika.",
                        "Fold in cheddar cheese and pour the mixture into the muffin tray.",
                        "Bake for 20 minutes until golden."))
                .build());

        recipeRepo.save(Recipe.builder()
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
                .build());

        recipeRepo.save(Recipe.builder()
                .title("Rice Noodles with Vegetables")
                .imgSrc("https://static01.nyt.com/images/2014/06/02/dining/Rice-Noodles/Rice-Noodles-superJumbo-v2.jpg")
                .description("A light and refreshing rice noodle dish with sautéed vegetables.")
                .ingredients(Arrays.asList("Rice noodles",
                        "Soy sauce",
                        "Carrots",
                        "Zucchini",
                        "Garlic cloves",
                        "Sesame oil"))
                .steps(Arrays.asList("Cook rice noodles according to package instructions.",
                        "Stir-fry garlic, carrots, and zucchini in sesame oil.",
                        "Add the cooked noodles and soy sauce, toss well, and serve."))
                .build());

    }
}