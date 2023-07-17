package ru.hogwarts.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.entity.Avatar;
import ru.hogwarts.school.entity.AvatarList;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudent_Id(Long studentId);

    @Query(value = "SELECT a.id, a.file_path, a.file_size, a.media_type, a.student_id FROM avatar AS a ", nativeQuery = true)
    Page<AvatarList> findAllAvatars(PageRequest pageRequest);

}
