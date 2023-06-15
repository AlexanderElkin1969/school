package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private Long generatedId = 1L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(generatedId);
        faculties.put(generatedId++, faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    public boolean isFoundFaculty(Long id) {
        return faculties.containsKey(id);
    }

    public Collection<Faculty> allFaculty() {
        return Collections.unmodifiableCollection(faculties.values());
    }

}
