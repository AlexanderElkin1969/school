package ru.hogwarts.school.model;

import java.util.Objects;

public class Faculty {

    private Long id;
    private String name;
    private String color;

    private static  Long counter = 0L;

    public Faculty(String name, String color) {
        this.id = ++ counter;
        this.name = name;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Student{" + '\'' +
                "id =" + id + '\'' +
                ", name ='" + name + '\'' +
                ", color =" + color + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return  id.equals(faculty.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }


}
