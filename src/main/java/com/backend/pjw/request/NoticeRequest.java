package com.backend.pjw.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
public class NoticeRequest {
    private Long id;
    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private List<MultipartFile> files;
    private ZonedDateTime startAt;
    private ZonedDateTime endAt;

    private String createdBy;

}
