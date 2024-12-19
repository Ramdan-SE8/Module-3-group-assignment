package group3.group3_assignment.exception;

public class FavUserNotFoundException extends RuntimeException {
    public FavUserNotFoundException(Long id) {
        super("Could not find user with id: " + id + ".");
    }
}
