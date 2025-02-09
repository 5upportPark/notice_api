package com.backend.pjw.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "notice")
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private List<String> files;

    private ZonedDateTime startAt;
    private ZonedDateTime endAt;

    private String createdBy;
    private ZonedDateTime createdAt;
    private String updatedBy;
    private ZonedDateTime updatedAt;

    @Builder
    public Notice(Long id, String title, String content, Long viewCount, List<String> files, ZonedDateTime startAt, ZonedDateTime endAt, String createdBy, ZonedDateTime createdAt, String updatedBy, ZonedDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.files = files;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
    }

    public static Notice fromNew(String title, String content, ZonedDateTime startAt, ZonedDateTime endAt, String createdBy){
        return Notice.builder()
                .title(title)
                .content(content)
                .startAt(startAt)
                .endAt(endAt)
                .createdBy(createdBy)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    public void update(String title, String content, ZonedDateTime startAt, ZonedDateTime endAt, String updatedBy){
        this.title = title;
        this.content = content;
        this.startAt = startAt;
        this.endAt = endAt;
        this.updatedBy = updatedBy;
        this.updatedAt = ZonedDateTime.now();
    }

}
