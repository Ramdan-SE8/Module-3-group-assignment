package group3.group3_assignment.exception;

public class RecipeAndUserNotFoundException extends RuntimeException {
    public RecipeAndUserNotFoundException(Integer id, Long userId) {
        super("Could not find recipe with id: " + id + " under user with id: " + userId + ".");
    }
}
