package ru.hogwarts.school.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class SchoolExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(SchoolExceptionHandler.class);

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> handlerNotFoundItem(NoSuchElementException e){
        logger.error("Element by this Id is absent.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element by this Id is absent. " + e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handlerIllegalArgument(IllegalArgumentException e){
        logger.error("Значения должны быть больше 0.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({NotFoundFacultyException.class})
    public ResponseEntity<?> handlerNotFoundFaculty(NotFoundFacultyException e){
        logger.error("There is not found faculty");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
