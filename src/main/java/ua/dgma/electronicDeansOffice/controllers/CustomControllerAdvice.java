package ua.dgma.electronicDeansOffice.controllers;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.dgma.electronicDeansOffice.exceptions.*;

import java.util.Date;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CustomException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, "Bad Request", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, e.getClass().getSimpleName(), e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 404, "Not Found", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotReadablePropertyException.class)
    private ResponseEntity<ErrorResponse> handleException(NotReadablePropertyException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, e.getClass().getSimpleName(), e.getLocalizedMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPropertyException.class)
    private ResponseEntity<ErrorResponse> handleException(IncorrectPropertyException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, "Incorrect Property", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectExistsException.class)
    private ResponseEntity<ErrorResponse> handleException(ObjectExistsException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, "Object exists", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<ErrorResponse> handleException(NullPointerException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, e.getClass().getSimpleName(), e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException e) {
        ErrorResponse response = new ErrorResponse(new Date(), 400, e.getClass().getSimpleName(), e.getCause().getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getClass().getSimpleName(),  e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
