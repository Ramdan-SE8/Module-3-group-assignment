package group3.group3_assignment.exception;

public class TokenNotGeneratedException extends RuntimeException {
    public TokenNotGeneratedException(String message) {
        super("Token generation failed: " + message);
    }
}
