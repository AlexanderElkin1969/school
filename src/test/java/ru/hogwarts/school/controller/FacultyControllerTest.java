package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @InjectMocks
    private FacultyController facultyController;

    Long id = 4L;
    String name = "TestName";
    String color = "TestColor";
    JSONObject facultyObject = new JSONObject();
    Faculty faculty = new Faculty();

    @BeforeEach
    public void initOut() throws JSONException {
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
    }

    @Test
    public void createFacultyTest() throws Exception{
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepository, only()).save(any());
    }

    @Test
    public void getFacultyTest() throws Exception{
        when(facultyRepository.findById(eq(4L))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        verify(facultyRepository, only()).findById(any());

        when(facultyRepository.findById(eq(5L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(facultyRepository, times(2)).findById(any());
    }

    @Test
    public void updateFacultyTest() throws Exception{
        String newName = "NewName";
        String newColor = "NewColor";
        faculty.setName(newName);
        faculty.setColor(newColor);
        facultyObject.put("name", newName);
        facultyObject.put("color", newColor);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyObject.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(newColor));

        verify(facultyRepository, only()).save(any());
    }

    @Test
    public void deleteFacultyTest() throws Exception{
        when(facultyRepository.findById(eq(4L))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/4")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        /*        .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color)); */

        verify(facultyRepository, only()).findById(any());
        verify(facultyRepository, only()).delete(any());

        when(facultyRepository.findById(eq(5L))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(facultyRepository, times(2)).findById(any());
    }

}
