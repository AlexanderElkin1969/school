package ru.hogwarts.school.entity;

public interface AvatarList {

    Long getId();
    String getFilePath();
    Integer getFileSize();
    String getMediaType();
    Student getStudent();

}
