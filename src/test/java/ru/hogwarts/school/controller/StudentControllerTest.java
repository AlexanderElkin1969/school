package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Faculty faculty;

    @BeforeEach
    public void init(){
        faculty = facultyRepository.save(new Faculty(1L,"Griffendor", "Red"));
    }

    @AfterEach
    public void clean(){
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void createStudentTest() throws Exception{
        Student testStudent = new Student( 1L, "Garry Potter", 13, faculty, "");
        ResponseEntity<Student> responseEntity = restTemplate.postForEntity(
                "http://localhost:"+port+"/student",
                testStudent,
                Student.class
        );
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotEquals(0L, responseEntity.getBody().getId());
        Assertions.assertEquals("Garry Potter", responseEntity.getBody().getName());
        Assertions.assertEquals(13, responseEntity.getBody().getAge());
    }

    @Test
    public void getStudentTest() throws Exception{
        Student testStudent = new Student( 1L, "Garry Potter", 13, faculty, "");
        ResponseEntity<Student> responseEntity = restTemplate.postForEntity(
                "http://localhost:"+port+"/student",
                testStudent,
                Student.class
        );
        Long id = responseEntity.getBody().getId();
        ResponseEntity<Student> resultEntity = restTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+id,
                Student.class
        );
        Assertions.assertEquals(HttpStatus.OK, resultEntity.getStatusCode());
        Assertions.assertEquals("Garry Potter", resultEntity.getBody().getName());
        Assertions.assertEquals(13, resultEntity.getBody().getAge());
        Assertions.assertEquals(id, resultEntity.getBody().getId());
    }

    @Test
    public void updateStudentTest() throws Exception{
        Student testStudent = new Student( 1L, "Garry Potter", 13, faculty, "");
        ResponseEntity<Student> responseEntity = restTemplate.postForEntity(
                "http://localhost:"+port+"/student",
                testStudent,
                Student.class
        );
        Long id = responseEntity.getBody().getId();
        testStudent.setId(id);
        testStudent.setName("Ron Weasley");
        ResponseEntity<Student> resultEntity = restTemplate.exchange(
                "http://localhost:"+port+"/student",
                HttpMethod.PUT,
                new HttpEntity<>(testStudent),
                Student.class
                );

        Assertions.assertEquals(HttpStatus.OK, resultEntity.getStatusCode());
        Assertions.assertEquals("Ron Weasley", resultEntity.getBody().getName());
        Assertions.assertEquals(13, resultEntity.getBody().getAge());
        Assertions.assertEquals(id, resultEntity.getBody().getId());
    }

}
