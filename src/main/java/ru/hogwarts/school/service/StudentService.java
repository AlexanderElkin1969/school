package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundFacultyException;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        Long id = student.getFaculty().getId();
        Optional<Faculty> facultyDB = facultyRepository.findById(id);
        facultyDB.orElseThrow(() -> new NotFoundFacultyException("Отсутствует информация о факультете."));
        student.setFaculty(facultyDB.get());
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("Was invoked method for find student by Id = {}", id);
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        Long id = student.getFaculty().getId();
        Optional<Faculty> facultyDB = facultyRepository.findById(id);
        facultyDB.orElseThrow(() -> new NotFoundFacultyException("Отсутствует информация о факультете."));
        student.setFaculty(facultyDB.get());
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        logger.info("Was invoked method for delete student by Id = {}", id);
        Student student = studentRepository.findById(id).get();
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> allStudentByAge(int age) {
        logger.info("Was invoked method for find all student by age");
        return Collections.unmodifiableCollection(studentRepository.findAllByAge(age));
    }

    public Collection<Student> allStudentByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find all student by age between");
        return Collections.unmodifiableCollection(studentRepository.findAllByAgeBetween(min, max));
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for find faculty by student by Id {}", id);
        return studentRepository.findById(id).get().getFaculty();
    }

    public Collection<Student> allStudent() {
        logger.info("Was invoked method for get all students");
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

    public int getCountStudents(){
        logger.info("Was invoked method for get count students");
        return studentRepository.getCountStudents();
    }

    public Float getAverageAge(){
        logger.info("Was invoked method for get average age students");
        return studentRepository.getAverageAge();
    }

    public Float getAverageAgeUsingStream(){
        logger.info("Was invoked method for get average age students using a stream");
        int sumAge = studentRepository.findAll().stream()
                .map(Student::getAge)
                .reduce(Integer::sum)
                .get();
        return ((float) sumAge)/getCountStudents();
    }

    public List<Student> getLastStudent(){
        logger.info("Was invoked method for get 5 students");
        return studentRepository.getLastStudent();
    }

    public List<String> getAllNameStartingWithA(){
        logger.info("Was invoked method for get all students with name starting A");
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase(Locale.ROOT))
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Student> getLastStudent(Integer page, Integer size){
        logger.info("Was invoked method for get page students by pageNumber = {}, pageSize = {}", page, size);
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return studentRepository.findAllFromLast(pageRequest).getContent();
    }

    public void test1(){
        logger.info("Was invoked method for test1");
        List<String> names = studentRepository.findAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        for (String name : names) {
            System.out.println(names.indexOf(name) + " " + name);
        }
        printString(names.get(0) + " thread_1");
        printString(names.get(1) + " thread_1");
        new Thread(() -> {
            printString(names.get(2) + " thread_2");
            printString(names.get(3) + " thread_2");
        }).start();
        new Thread(() -> {
            printString(names.get(4) + " thread_3");
            printString(names.get(5) + " thread_3");
        }).start();
    }

    private void printString(String string){
        try {
            Thread.sleep(5000);
            System.out.println(string);
        }catch (InterruptedException e){
            e.getLocalizedMessage();
        }
    }
}
