package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.*;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RequestMapping ("student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student createStudent = studentService.createStudent(student);
        if (createStudent == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        if (!studentService.isFoundStudent(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        if (!studentService.isFoundStudent(student.getId())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        if (!studentService.isFoundStudent(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("age/{age}")
    public Collection<Student> getAllByAge(@PathVariable int age){
        return studentService.allStudentByAge(age);
    }

    @GetMapping
    public Collection<Student> getAll(){
        return studentService.allStudent();
    }

}
