package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for find faculty by Id = {}", id);
        return facultyRepository.findById(id).get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty by Id = {}", id);
        Faculty faculty = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> allFacultyByColor(String color) {
        logger.info("Was invoked method for get all faculty by Color = {}", color);
        return Collections.unmodifiableCollection(facultyRepository.findAllByColor(color));
    }

    public Collection<Faculty> allFacultyByColorOrNameIgnoreCase(String color, String name) {
        logger.info("Was invoked method for get all faculty by Color = {} or Name = {}", color, name);
        return Collections.unmodifiableCollection(facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(color, name));
    }

    public Collection<Student> allStudentByFaculty(Long id) {
        logger.info("Was invoked method for get all student by faculty by Id = {}", id);
        Faculty faculty = facultyRepository.findById(id).get();
        return Collections.unmodifiableCollection(studentRepository.findAllByFaculty(faculty));
    }

    public Collection<Faculty> allFaculty() {
        logger.info("Was invoked method for get all faculty");
        return Collections.unmodifiableCollection(facultyRepository.findAll());
    }

    public String getLongestName(){
        logger.info("Was invoked method for get longest name");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .get();
    }

}
