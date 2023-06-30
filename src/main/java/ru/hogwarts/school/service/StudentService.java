package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.*;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> allStudentByAge(int age) {
        return Collections.unmodifiableCollection(studentRepository.findByAge(age));
    }

    public Collection<Student> allStudentByAgeBetween(int min, int max) {
        return Collections.unmodifiableCollection(studentRepository.findByAgeBetween(min, max));
    }

    public Collection<Student> allStudent() {
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

}
