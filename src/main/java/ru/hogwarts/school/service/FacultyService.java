package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> allFacultyByColor(String color) {
        return Collections.unmodifiableCollection(facultyRepository.findAllByColor(color));
    }

    public Collection<Faculty> allFacultyByColorOrNameIgnoreCase(String color, String name) {
        return Collections.unmodifiableCollection(facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name));
    }

    public Collection<Student> allStudentByFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id).get();
        return Collections.unmodifiableCollection(studentRepository.findAllByFaculty(faculty));
    }

    public Collection<Faculty> allFaculty() {
        return Collections.unmodifiableCollection(facultyRepository.findAll());
    }

}
