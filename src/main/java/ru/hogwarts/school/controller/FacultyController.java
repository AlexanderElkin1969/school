package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RequestMapping("faculty")
@RestController
@Tag(name = "Контроллер для работы с факультетами")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }

    @GetMapping("color/{color}")
    public Collection<Faculty> getAllByColor(@PathVariable String color) {
        return facultyService.allFacultyByColor(color);
    }

    @GetMapping("color/{color}/orName/{name}")
    public Collection<Faculty> getAllByColorOrNameIgnoreCase(@PathVariable String color, @PathVariable String name) {
        return facultyService.allFacultyByColorOrNameIgnoreCase(color, name);
    }

    @GetMapping("{id}/students")
    public Collection<Student> getStudentByFaculty(@PathVariable Long id) {
        return facultyService.allStudentByFaculty(id);
    }

    @GetMapping("all")
    public Collection<Faculty> getAll() {
        return facultyService.allFaculty();
    }

}
