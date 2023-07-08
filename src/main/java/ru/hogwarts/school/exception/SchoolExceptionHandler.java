package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class SchoolExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handlerNotFoundItem(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element by this ID is absent. " + e.getMessage());
    }

    @ExceptionHandler({NotFoundFacultyException.class})
    public ResponseEntity<?> handlerNotFoundFaculty(NotFoundFacultyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
