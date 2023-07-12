package ru.hogwarts.school.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        Mockito.reset(facultyRepository);

        when(facultyRepository.findById(eq(5L))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(facultyRepository, only()).findById(any());
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
        Mockito.reset(facultyRepository);

        when(facultyRepository.findById(eq(5L))).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        verify(facultyRepository, only()).findById(any());
    }

    @Test
    public void getAllTest() throws Exception{
        List<Faculty> faculties = new ArrayList<>(List.of(
                new Faculty(1L, "TestName", "Color"),
                new Faculty(2L, "Name", "TestColor"),
                new Faculty(3L, "Name", "Color")
        ));
        when(facultyRepository.findAll()).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("TestName"))
                .andExpect(jsonPath("$[1].name").value("Name"))
                .andExpect(jsonPath("$[2].name").value("Name"))
                .andExpect(jsonPath("$[2].color").value("Color"));
        verify(facultyRepository, only()).findAll();
        Mockito.reset(facultyRepository);

        when(facultyRepository.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facultyRepository, only()).findAll();
    }

    @Test
    public void getAllByColorTest() throws Exception{
        List<Faculty> faculties = new ArrayList<>(List.of(
                new Faculty(1L, "TestName", "Color"),
                new Faculty(3L, "Name", "Color")
        ));
        when(facultyRepository.findAllByColor(any())).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/Color")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("TestName"))
                .andExpect(jsonPath("$[0].color").value("Color"))
                .andExpect(jsonPath("$[1].id").value(3L))
                .andExpect(jsonPath("$[1].name").value("Name"))
                .andExpect(jsonPath("$[1].color").value("Color"));
        verify(facultyRepository, only()).findAllByColor(any());
        Mockito.reset(facultyRepository);

        when(facultyRepository.findAllByColor(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color/Color")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(facultyRepository, only()).findAllByColor(any());
    }

}
