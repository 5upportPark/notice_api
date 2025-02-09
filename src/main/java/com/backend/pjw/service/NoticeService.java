package com.backend.pjw.service;

import com.backend.pjw.entity.Notice;
import com.backend.pjw.repository.FileRepositoryImpl;
import com.backend.pjw.repository.NoticeRepositoryImpl;
import com.backend.pjw.request.NoticeRequest;
import com.backend.pjw.view.NoticeView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepositoryImpl noticeRepositoryImpl;
    private final FileRepositoryImpl fileRepositoryImpl;

    public NoticeService(NoticeRepositoryImpl noticeRepositoryImpl, FileRepositoryImpl fileRepositoryImpl) {
        this.noticeRepositoryImpl = noticeRepositoryImpl;
        this.fileRepositoryImpl = fileRepositoryImpl;
    }

    @Transactional(readOnly = true)
    public List<NoticeView> getNoticeList(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Notice> notices = noticeRepositoryImpl.findAllByOrderByIdDesc(pageable);
        return notices.stream().map(NoticeView::fromEntity).toList();
    }

    @Transactional
    public void addNotice(NoticeRequest req){
        Notice notice = Notice.fromNew(req.getTitle(), req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        noticeRepositoryImpl.save(notice);
    }

    @Transactional
    public void editNotice(NoticeRequest req){
        Optional<Notice> optNotice = noticeRepositoryImpl.findById(req.getId());
        if(optNotice.isEmpty()) return;

        Notice notice = optNotice.get();
        notice.update(req.getTitle(), req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        noticeRepositoryImpl.save(notice);
    }

    @Transactional
    public void deleteNotice(Long id){
        noticeRepositoryImpl.deleteById(id);
    }
}
