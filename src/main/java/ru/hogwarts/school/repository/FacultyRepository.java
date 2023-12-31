package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.entity.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findAllByColor(String color);

    List<Faculty> findAllByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

}
