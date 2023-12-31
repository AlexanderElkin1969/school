package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotFoundFacultyException extends RuntimeException{

    public NotFoundFacultyException(String message) {
        super(message);
    }
}
