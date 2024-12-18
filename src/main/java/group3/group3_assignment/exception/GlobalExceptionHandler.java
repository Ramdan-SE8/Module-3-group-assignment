package group3.group3_assignment.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecipeNotFoundException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        logger.warn(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotAuthorizeException.class)
    public ResponseEntity<ErrorResponse> UserNotAuthorizeException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        logger.warn(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
        logger.warn(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // General exception
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("There is an unexpected error", LocalDateTime.now());
        logger.warn(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // validation exception handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        // get a list of validation errors
        List<ObjectError> validationErrors = e.getBindingResult().getAllErrors();

        // create a stringBuilder to store all message
        StringBuilder sb = new StringBuilder();
        // Loop through all the errors and append the error messages

        for (ObjectError error : validationErrors) {
            sb.append(error.getDefaultMessage() + ".");
        }
        ErrorResponse errorResponse = new ErrorResponse(sb.toString(), LocalDateTime.now());
        logger.warn(errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
