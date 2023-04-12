package ro.fasttrackit.course9.homework.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class RoomControllerAdvice {
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    ApiError handleValidationException(ValidationException exception) {
        return new ApiError("VALIDATION-ERROR", exception.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    ApiError handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ApiError("RESOURCE-ERROR", exception.getMessage());
    }
}
