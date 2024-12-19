package group3.group3_assignment.exception;

public class FavouritesNotFoundException extends RuntimeException {
    public FavouritesNotFoundException() {
        super("No such recipe or user found with a favourites list");
    }
}
