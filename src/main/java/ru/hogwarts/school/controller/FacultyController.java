package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.*;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RequestMapping ("faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity createFaculty(@RequestBody Faculty faculty){
        Faculty createFaculty = facultyService.createFaculty(faculty);
        if (createFaculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity getFaculty(@PathVariable Long id){
        if (!facultyService.isFoundFaculty(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    @PutMapping
    public ResponseEntity updateFaculty(@RequestBody Faculty faculty) {
        if (!facultyService.isFoundFaculty(faculty.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.updateFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable Long id){
        if (!facultyService.isFoundFaculty(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }

    @GetMapping("color/{color}")
    public Collection<Faculty> getAllByColor(@PathVariable String color){
        return facultyService.allFacultyByColor(color);
    }

    @GetMapping
    public Collection<Faculty> getAll(){
        return facultyService.allFaculty();
    }

}
