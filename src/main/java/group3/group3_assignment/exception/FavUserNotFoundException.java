package group3.group3_assignment.exception;

public class FavUserNotFoundException extends RuntimeException {
    public FavUserNotFoundException() {
        super("Could not find recipe in favourites list");
    }
}
