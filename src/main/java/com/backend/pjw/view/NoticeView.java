package com.backend.pjw.view;

import com.backend.pjw.entity.Notice;
import com.backend.pjw.entity.NoticeProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeView {
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

    public static NoticeView fromEntity(Notice notice){
        return NoticeView.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .viewCount(notice.getViewCount())
                .startAt(notice.getStartAt())
                .endAt(notice.getEndAt())
                .createdBy(notice.getCreatedBy())
                .createdAt(notice.getCreatedAt())
                .updatedBy(notice.getUpdatedBy())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

    public static NoticeView from(NoticeProjection notice){
        return NoticeView.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .viewCount(notice.getViewCount())
                .createdBy(notice.getCreatedBy())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    @Builder
    public NoticeView(Long id, String title, String content, Long viewCount, List<String> files, ZonedDateTime startAt, ZonedDateTime endAt, String createdBy, ZonedDateTime createdAt, String updatedBy, ZonedDateTime updatedAt) {
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
}
