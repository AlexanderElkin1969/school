package ru.hogwarts.school.model;

import java.util.Objects;

public class Student {
    private Long id;
    private String name;
    private int age;

    private static  Long counter = 0L;

    public Student(String name, int age) {
        this.id = ++ counter;
        this.name = name;
        this.age = age;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" + '\'' +
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
        return  id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }

}
