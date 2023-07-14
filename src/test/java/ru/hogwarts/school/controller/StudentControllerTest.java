package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createAndGetAndDeleteStudentTest() throws Exception{
        //  createStudentTest
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

        //  getStudentTest
        Long id = responseEntity.getBody().getId();
        ResponseEntity<Student> goodResultEntity = restTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+id,
                Student.class
        );
        Assertions.assertEquals(HttpStatus.OK, goodResultEntity.getStatusCode());
        Assertions.assertEquals("Garry Potter", goodResultEntity.getBody().getName());
        Assertions.assertEquals(13, goodResultEntity.getBody().getAge());
        Assertions.assertEquals(id, goodResultEntity.getBody().getId());

        ResponseEntity<String> badResultEntity = restTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+2L,
                String.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, badResultEntity.getStatusCode());

        //  deleteStudentTest
        restTemplate.delete("http://localhost:"+port+"/student/"+id);
        ResponseEntity<String> foundEntity = restTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+id,
                String.class
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, foundEntity.getStatusCode());
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

        //  Факультета с id = 2 нет в базе, а поле faculty notNull
        Faculty newFaculty = new Faculty();
        newFaculty.setId(2L);
        testStudent.setFaculty(newFaculty);
        ResponseEntity<String> badResultEntity = restTemplate.exchange(
                "http://localhost:"+port+"/student",
                HttpMethod.PUT,
                new HttpEntity<>(testStudent),
                String.class
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, badResultEntity.getStatusCode());
    }

    @Test
    public void getAllAndGetByAgeAndGetAllByAgeBetweenTest() throws Exception{
        //  getAllTest
        ResponseEntity<List<Student>> actualEmptyList = restTemplate.exchange(
                "http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions.assertEquals(HttpStatus.OK, actualEmptyList.getStatusCode());
        Assertions.assertArrayEquals(Collections.emptyList().toArray(), actualEmptyList.getBody().toArray());

        List<Student> students = new ArrayList<>( List.of(
                new Student( 1L, "Garry Potter", 13, faculty, ""),
                new Student( 2L, "Ron Weasley", 13, faculty, ""),
                new Student( 3L, "Ginevra Weasley", 12, faculty, "")
        ));
        for (int i = 0; i < 3; i++) {
            ResponseEntity<Student> responseEntity = restTemplate.postForEntity(
                    "http://localhost:" + port + "/student",
                    students.get(i),
                    Student.class
            );
        }
        ResponseEntity<List<Student>> actual = restTemplate.exchange(
                "http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertArrayEquals(students.toArray(), actual.getBody().toArray());

        //  GetByAgeTest
        List<Student> expectedByAge13 = students.stream().
                filter(student -> student.getAge() == 13)
                .collect(Collectors.toList());
        ResponseEntity<List<Student>> actualByAge13 = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/"+13,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions.assertEquals(HttpStatus.OK, actualByAge13.getStatusCode());
        Assertions.assertArrayEquals(expectedByAge13.toArray(), actualByAge13.getBody().toArray());

        ResponseEntity<List<Student>> actualByAge0 = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/"+0,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions.assertEquals(HttpStatus.OK, actualByAge0.getStatusCode());
        Assertions.assertArrayEquals(Collections.emptyList().toArray(), actualByAge0.getBody().toArray());

        //  getAllByAgeBetweenTest
        List<Student> expectedByAge10Between12 = students.stream().
                filter(student -> student.getAge() >= 10).
                filter(student -> 12 >= student.getAge()).
                collect(Collectors.toList());
        ResponseEntity<List<Student>> actualByAge10Between12 = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/"+10+"/between/"+12,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        Assertions.assertEquals(HttpStatus.OK, actualByAge10Between12.getStatusCode());
        Assertions.assertArrayEquals(expectedByAge10Between12.toArray(), actualByAge10Between12.getBody().toArray());
    }

    @Test
    public void getFacultyTest() throws Exception {
        Student testStudent = new Student(1L, "Garry Potter", 13, faculty, "");
        ResponseEntity<Student> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                testStudent,
                Student.class
        );
        Long id = responseEntity.getBody().getId();
        ResponseEntity<Faculty> resultEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + id + "/faculty",
                Faculty.class
        );
        Assertions.assertEquals(HttpStatus.OK, resultEntity.getStatusCode());
        Assertions.assertEquals(faculty.getName(), resultEntity.getBody().getName());
        Assertions.assertEquals(faculty.getColor(), resultEntity.getBody().getColor());
        Assertions.assertEquals(faculty.getId(), resultEntity.getBody().getId());
    }
}
