package ru.hogwarts.school.model;

import java.util.Objects;

public class Student {
    private Long id;
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
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
