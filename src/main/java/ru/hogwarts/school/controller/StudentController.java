package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;

@RequestMapping("student")
@RestController
@Tag(name = "Контроллер для работы со студентами")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getAllByAge(@PathVariable int age) {
        return ResponseEntity.ok(studentService.allStudentByAge(age));
    }

    @GetMapping("age/{min}/between/{max}")
    public ResponseEntity<Collection<Student>> getAllByAgeBetween(@PathVariable int min, @PathVariable int max) {
        return ResponseEntity.ok(studentService.allStudentByAgeBetween(min, max));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFacultyById(id));
    }

    @GetMapping("all")
    public ResponseEntity<Collection<Student>> getAll() {
        return ResponseEntity.ok(studentService.allStudent());
    }

    @PatchMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 200) {
            return ResponseEntity.badRequest().body("Файл слишком большой. Размер не должен превышать 200 Кбайт.");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all/count")
    public int getCountStudents() {
        return studentService.getCountStudents();
    }

    @GetMapping("all/age/average")
    public Float getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("all/last-5")              // Лучше использовать следующий запрос с параметрами 1 и 5
    public ResponseEntity<Collection<Student>> getLastStudent(){
        return ResponseEntity.ok(studentService.getLastStudent());
    }

    @GetMapping("all/from-the-last/{page}/to/{sizePage}")
    public ResponseEntity<Collection<Student>> getLastStudent(@PathVariable Integer page,
                                                              @PathVariable Integer sizePage){
        if (page <= 0 || sizePage <= 0){ throw new IllegalArgumentException("Значения должны быть больше 0.");}
        return ResponseEntity.ok(studentService.getLastStudent(page, sizePage));
    }

    @GetMapping("all/name-stsrting-A")
    public ResponseEntity<Collection<String>> getAllNameStartingWithA() {
        return ResponseEntity.ok(studentService.getAllNameStartingWithA());
    }

}
