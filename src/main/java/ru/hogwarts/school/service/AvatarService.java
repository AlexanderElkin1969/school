package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.AvatarList;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${avatar.dir.path}")
    private String avatarDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId).get();
        Avatar avatar = findAvatarByStudentId(studentId);
        if (avatar.getFilePath() != null){
            Path oldFilePath = Path.of(avatar.getFilePath());
            Files.deleteIfExists(oldFilePath);
        }
        Path filePath = Path.of(avatarDir, studentId + "."
                + StringUtils.getFilenameExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());

        try(InputStream is = file.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ){
            bis.transferTo(bos);
        }

        avatar.setFilePath(filePath.toString());
        avatar.setFileSize((int) file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(student);
        student.setAvatarUrl("http://localhost:8080/avatars/" + avatar.getId() + "/avatar-from-db");

        avatarRepository.save(avatar);
    }

    public Avatar findAvatarByStudentId(Long studentId){
        return avatarRepository.findByStudent_Id(studentId).orElse(new Avatar());
    }

    public Avatar getFromDb(Long id){
        return avatarRepository.findById(id).get();
    }

    public List<AvatarList> getAvatars(Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return avatarRepository.findAllAvatars(pageRequest).getContent();
    }

}
