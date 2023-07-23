package ru.hogwarts.school.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.AvatarList;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Collection;

@RequestMapping("avatars")
@RestController
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping(value = "{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatarFromDb(@PathVariable Long id) {
        Avatar avatar = avatarService.getFromDb(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "{id}/avatar-from-file")
    public void downloadAvatarFromFile(@PathVariable Long id, HttpServletResponse response) throws IOException{
        Avatar avatar = avatarService.getFromDb(id);
        Path path = Path.of(avatar.getFilePath());
        try(InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength(avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("all/{page}/to/{sizePage}")
    public ResponseEntity<Collection<AvatarList>> getAvatars(@PathVariable Integer page,
                                                             @PathVariable Integer sizePage){
        if (page <= 0 || sizePage <= 0){ throw new IllegalArgumentException("Значения должны быть больше 0.");}
        return ResponseEntity.ok(avatarService.getAvatars(page, sizePage));
    }

}
