package com.moreffnest.usersapi.exception;

import com.moreffnest.usersapi.dto.ApiError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UserExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError userNotFoundHandler(UserNotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.NOT_FOUND.value());
        apiError.setMessage("There is no such user!");
        apiError.setErrors(List.of(ex.getMessage()));

        return apiError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiError handleRequestValidationExceptions(
            MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(ex.getBody().getStatus());
        apiError.setMessage("Wrong method arguments!");
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add("%s: %s".formatted( fieldName, errorMessage));
        });
        apiError.setErrors(errors);

        return apiError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public ApiError handleDatabaseValidationExceptions(
            ConstraintViolationException ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST.value());
        apiError.setMessage("Wrong arguments for User entity!");
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach((error) -> {
            errors.add(error.getMessage());
        });
        apiError.setErrors(errors);

        return apiError;
    }



}
