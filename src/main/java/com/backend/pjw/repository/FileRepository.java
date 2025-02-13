package com.backend.pjw.repository;

import com.backend.pjw.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByParentId(Long id);
    void deleteAllByParentId(Long id);
}
