package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private Long generatedId = 1L;


    public Student createStudent(Student student) {
        student.setId(generatedId);
        students.put(generatedId++, student);
        return student;
    }

    public Student getStudentById(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public boolean isFoundStudent(Long id) {
        return students.containsKey(id);
    }

    public Collection<Student> allStudent() {
        return Collections.unmodifiableCollection(students.values());
    }

}
