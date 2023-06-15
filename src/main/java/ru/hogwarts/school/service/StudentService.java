package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    public StudentService() {
    }

    public Student createStudent(Student student){
        students.put(student.getId(), student);
        return student;
    }

    public Student getStudentById(Long id){
        return students.get(id);
    }

    public Student updateStudent(Long id, Student student){
        students.put(id, student);
        return student;
    }

    public Student deleteStudent(Long id){
        return students.remove(id);
    }

    public boolean isFoundStudent(Long id){
        return students.containsKey(id);
    }

}
