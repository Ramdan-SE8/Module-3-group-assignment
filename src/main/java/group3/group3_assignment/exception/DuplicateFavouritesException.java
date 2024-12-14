package group3.group3_assignment.exception;

public class DuplicateFavouritesException extends RuntimeException {
    public DuplicateFavouritesException() {
        super("Recipe is already in favourites list");
    }
}
