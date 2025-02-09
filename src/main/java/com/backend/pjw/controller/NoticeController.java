package com.backend.pjw.controller;

import com.backend.pjw.request.NoticeRequest;
import com.backend.pjw.service.NoticeService;
import com.backend.pjw.view.NoticeView;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public List<NoticeView> getNoticeList(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
        return noticeService.getNoticeList(page, pageSize);
    }

    @PostMapping
    public void addNotice(@ModelAttribute @Valid NoticeRequest req){
        noticeService.addNotice(req);
    }

    @PutMapping
    public void editNotice(@ModelAttribute @Valid NoticeRequest req){
        noticeService.editNotice(req);
    }

    @DeleteMapping
    public void deleteNotice(@RequestParam("id") Long id){
        noticeService.deleteNotice(id);
    }
}
