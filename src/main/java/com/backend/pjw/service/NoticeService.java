package com.backend.pjw.service;

import com.backend.pjw.entity.File;
import com.backend.pjw.entity.Notice;
import com.backend.pjw.repository.FileRepositoryImpl;
import com.backend.pjw.repository.NoticeRepositoryImpl;
import com.backend.pjw.request.NoticeRequest;
import com.backend.pjw.util.FileUtil;
import com.backend.pjw.view.NoticeView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    public NoticeView addNotice(NoticeRequest req){
        Notice notice = Notice.fromNew(req.getTitle(), req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        noticeRepositoryImpl.save(notice);
        List<File> fileList = newFileUpload(notice.getId(), req.getFiles());
        fileRepositoryImpl.saveAll(fileList);

        return NoticeView.fromEntity(notice);
    }

    @Transactional
    public NoticeView editNotice(NoticeRequest req){
        Optional<Notice> optNotice = noticeRepositoryImpl.findById(req.getId());
        if(optNotice.isEmpty()) return null;

        fileRepositoryImpl.deleteAllByParentId(req.getId());

        List<File> fileList = newFileUpload(req.getId(), req.getFiles());
        fileRepositoryImpl.saveAll(fileList);

        Notice notice = optNotice.get();
        notice.update(req.getTitle(), req.getContent(), req.getStartAt(), req.getEndAt(), req.getCreatedBy());
        noticeRepositoryImpl.save(notice);

        return NoticeView.fromEntity(notice);
    }

    @Transactional
    public void deleteNotice(Long id){
        fileRepositoryImpl.deleteAllByParentId(id);
        noticeRepositoryImpl.deleteById(id);
    }

    private List<File> newFileUpload(Long noticeId, List<MultipartFile> files){
        List<File> fileList = new ArrayList<>();
        for(MultipartFile file : files) {
            String path = FileUtil.uploadFile(file);
            File fileEntity = File.newOne(noticeId, file.getOriginalFilename(), file.getName(), path);
            fileList.add(fileEntity);
        }
        return fileList;
    }
}
