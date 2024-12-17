package group3.group3_assignment.exception;

public class UserNotAuthorizeException extends RuntimeException {
    public UserNotAuthorizeException(Integer userId, String action) {
        super("User with id: " + userId + "is not authorize to" + action + "the recipe.");
    }
}