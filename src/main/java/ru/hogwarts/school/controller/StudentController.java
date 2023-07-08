package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RequestMapping("student")
@RestController
@Tag(name = "Контроллер для работы со студентами")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("age/{age}")
    public Collection<Student> getAllByAge(@PathVariable int age) {
        return studentService.allStudentByAge(age);
    }

    @GetMapping("age/{min}/between/{max}")
    public Collection<Student> getAllByAgeBetween(@PathVariable int min, @PathVariable int max) {
        return studentService.allStudentByAgeBetween(min, max);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFacultyById(id));
    }

    @GetMapping("all")
    public Collection<Student> getAll() {
        return studentService.allStudent();
    }

}
