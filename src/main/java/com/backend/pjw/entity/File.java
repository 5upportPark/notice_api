package com.backend.pjw.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Entity
@Table(name = "file")
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId;
    private String originName;
    private String name;
    private String path;
    private ZonedDateTime createdAt;

    public static File newOne(Long parentId, String originName, String name, String path){
        return  File.builder()
                .parentId(parentId)
                .originName(originName)
                .name(name)
                .path(path)
                .createdAt(ZonedDateTime.now())
                .build();
    }

    @Builder
    public File(Long id, Long parentId, String originName, String name, String path, ZonedDateTime createdAt) {
        this.id = id;
        this.parentId = parentId;
        this.originName = originName;
        this.name = name;
        this.path = path;
        this.createdAt = createdAt;
    }
}
