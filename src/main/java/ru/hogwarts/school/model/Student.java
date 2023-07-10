package ru.hogwarts.school.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @ManyToOne
    @JoinColumn(nullable = false, name = "faculty_id")
    private Faculty faculty;
    private String avatarUrl;

    protected Student() {
    }

    public Student(Long id, String name, int age, Faculty faculty, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.faculty = faculty;
        this.avatarUrl = avatarUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return  id.equals(student.id)&&Objects.equals(name,student.getName())&&age==student.getAge();
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }

}
