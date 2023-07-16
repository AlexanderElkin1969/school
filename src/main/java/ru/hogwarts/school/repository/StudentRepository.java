package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findAllByAge(int age);

    List<Student> findAllByAgeBetween(int nin, int max);

    List<Student> findAllByFaculty(Faculty faculty);

    @Query(value = "SELECT count(*) FROM student", nativeQuery = true)
    int getCountStudents();

    @Query(value = "SELECT avg(age) FROM student", nativeQuery = true)
    Float getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastStudent();

}
