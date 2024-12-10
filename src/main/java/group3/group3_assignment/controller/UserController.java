package group3.group3_assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.ok(newUser);
    }

    // @GetMapping("/{userId}/recipes")
    // public ResponseEntity<List<Recipe>> getUserRecipes(@PathVariable Integer
    // userId) {
    // List<Recipe> recipes = userService.getRecipesByUser(userId);
    // return ResponseEntity.ok(recipes);
    // }

    // @PostMapping("/{userId}/recipes")
    // public ResponseEntity<Recipe> uploadRecipe(@PathVariable Integer userId,
    // @RequestBody Recipe recipe) {
    // Recipe newRecipe = userService.uploadRecipe(userId, recipe);
    // return ResponseEntity.ok(newRecipe);
    // }
}
