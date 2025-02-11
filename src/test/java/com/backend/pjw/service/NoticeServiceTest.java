package com.backend.pjw.service;

import com.backend.pjw.entity.Notice;
import com.backend.pjw.repository.FileRepositoryImpl;
import com.backend.pjw.repository.NoticeRepositoryImpl;
import com.backend.pjw.request.NoticeRequest;
import com.backend.pjw.view.NoticeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceTest {
    @Mock
    NoticeRepositoryImpl noticeRepositoryImpl;
    @Mock
    FileRepositoryImpl fileRepositoryImpl;
    @InjectMocks
    NoticeService noticeService;

    private List<Notice> noticeList;
    private Notice notice;
    @BeforeEach
    public void setUp(){
        notice = Notice.fromNew("title","content",ZonedDateTime.now(), ZonedDateTime.now().plusDays(1), "tester");

        noticeList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Notice notice = Notice.builder()
                    .id((long) i)
                    .title("title"+i)
                    .content("content"+i)
                    .viewCount(0L)
                    .createdBy("tester"+i)
                    .createdAt(ZonedDateTime.now())
                    .build();
            noticeList.add(notice);
        }
    }
    @Test
    @DisplayName("공지사항 조회 테스트")
    public void getNoticeList(){
        Pageable page = PageRequest.of(0,10);
        when(noticeRepositoryImpl.findAllByOrderByIdDesc(any(Pageable.class)))
                .thenReturn(noticeList);

        List<NoticeView> result = noticeService.getNoticeList(0, 10);
        NoticeView noticeView = result.get(0);
System.out.println(result.get(0).getViewCount()+", "+result.get(1).getViewCount()+", "+result.get(2).getViewCount());
        assertThat(result).hasSize(3);
        assertEquals(noticeView.getTitle(), "title0");
        assertEquals(noticeView.getContent(), "content0");
        assertThat(noticeView.getCreatedAt()).isNotNull();
        assertThat(noticeView.getViewCount()).isNotNegative();
        assertEquals(noticeView.getCreatedBy(), "tester0");
        verify(noticeRepositoryImpl, times(1)).findAllByOrderByIdDesc(any(Pageable.class));
    }

    @Test
    @DisplayName("공지사항 작성  테스트")
    public void addNotice(){
        NoticeRequest req = new NoticeRequest();
        req.setTitle("title");
        req.setContent("content");
        req.setCreatedBy("tester");
        req.setStartAt(ZonedDateTime.now());
        req.setEndAt(ZonedDateTime.now().plusDays(7));
        req.setFiles(Collections.emptyList());

        Notice notice = Notice.fromNew(req.getTitle(), req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        when(noticeRepositoryImpl.save(any(Notice.class))).thenReturn(notice);
        when(fileRepositoryImpl.saveAll(anyList())).thenReturn(Collections.emptyList());

        NoticeView noticeView = noticeService.addNotice(req);

        assertEquals(req.getTitle(), noticeView.getTitle());
        assertEquals(req.getContent(), noticeView.getContent());
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    public void editNotice(){
        NoticeRequest req = new NoticeRequest();
        req.setId(1L);
        req.setTitle("title");
        req.setContent("content");
        req.setCreatedBy("tester");
        req.setStartAt(ZonedDateTime.now());
        req.setEndAt(ZonedDateTime.now().plusDays(7));
        req.setFiles(Collections.emptyList());

        when(noticeRepositoryImpl.findById(anyLong())).thenReturn(Optional.of(notice));
        when(noticeRepositoryImpl.save(any(Notice.class))).thenReturn(notice);

        doNothing().when(fileRepositoryImpl).deleteAllByParentId(anyLong());
        when(fileRepositoryImpl.saveAll(anyList())).thenReturn(Collections.emptyList());

        NoticeView result = noticeService.editNotice(req);

        assertThat(result).isNotNull();
        assertEquals(result.getTitle(), req.getTitle());
        assertEquals(result.getContent(), req.getContent());

    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    public void deleteNotice(){
        long id = 1L;
        doNothing().when(noticeRepositoryImpl).deleteById(anyLong());
        doNothing().when(fileRepositoryImpl).deleteAllByParentId(anyLong());

        noticeService.deleteNotice(id);

        verify(fileRepositoryImpl, times(1)).deleteAllByParentId(id);
        verify(noticeRepositoryImpl, times(1)).deleteById(id);
    }
}
