package com.backend.pjw.controller;

import com.backend.pjw.request.NoticeRequest;
import com.backend.pjw.service.NoticeService;
import com.backend.pjw.view.NoticeView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@DisplayName("공지사항 API 컨트롤러")
@AutoConfigureMockMvc
@WebMvcTest(NoticeController.class)
public class NoticeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private NoticeService noticeService;

    private List<NoticeView> noticeList;
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private NoticeView noticeView;
    private MockMultipartFile file;
    @BeforeEach
    public void setUp() throws IOException {
        noticeList = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            NoticeView notice = NoticeView.builder()
                    .id((long) i)
                    .title("title"+i)
                    .content("content"+i)
                    .viewCount(0L)
                    .createdBy("tester"+1)
                    .build();
            noticeList.add(notice);
        }

        ZonedDateTime startAt = ZonedDateTime.now();
        ZonedDateTime endAt = ZonedDateTime.now().plusDays(7);
        noticeView = NoticeView.builder()
                .id(1L)
                .title("title")
                .content("content")
                .startAt(startAt)
                .endAt(endAt)
                .createdBy("tester")
                .build();

        file = new MockMultipartFile("image1", "java.png", "png",
                new FileInputStream("src/test/resources/testImage/java.png"));
    }

    @Test
    @DisplayName("공지사항 조회 API 테스트")
    public void getNoticeList() throws Exception {
        when(noticeService.getNoticeList(anyLong())).thenReturn(noticeList);

        mockMvc.perform(get("/notice?page=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].title").value("title0"));

    }

    @Test
    @DisplayName("공지사항 작성 API 테스트")
    public void addNotice() throws Exception {
        when(noticeService.addNotice(any(NoticeRequest.class))).thenReturn(noticeView);

        mockMvc.perform(multipart("/notice")
                .file(file)
                .param("title","title").param("content", "content")
                .param("createdBy","tester")
                .param("startAt", ZonedDateTime.now().format(dateTimeFormat))
                .param("endAt",ZonedDateTime.now().plusDays(7).format(dateTimeFormat))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("공지사항 수정 API 테스트")
    public void editNotice() throws Exception {
        when(noticeService.editNotice(any(NoticeRequest.class))).thenReturn(noticeView);

        mockMvc.perform(multipart("/notice")
                .file(file)
                .param("title","title").param("content", "content")
                .param("createdBy","tester")
                .param("startAt", ZonedDateTime.now().format(dateTimeFormat))
                .param("endAt",ZonedDateTime.now().plusDays(7).format(dateTimeFormat))
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("공지사항 삭제 API 테스트")
    public void deleteNotice() throws Exception {
        doNothing().when(noticeService).deleteNotice(anyLong());

        mockMvc.perform(delete("/notice?id=1"))
                .andExpect(status().isOk());
    }
}
