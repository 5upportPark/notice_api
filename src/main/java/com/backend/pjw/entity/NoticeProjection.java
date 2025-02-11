package com.backend.pjw.entity;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class NoticeProjection {
    private String title;
    private String content;
    private Long viewCount;
    private String createdBy;
    private ZonedDateTime createdAt;

    public NoticeProjection(String title, String content, Long viewCount, String createdBy, ZonedDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }
}
