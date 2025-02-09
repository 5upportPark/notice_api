package com.backend.pjw.repository;

import com.backend.pjw.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByIdDesc(Pageable pageable);
    Optional<Notice> findById(Long id);
    Notice save(Notice notice);
    void deleteById(Long id);
}
