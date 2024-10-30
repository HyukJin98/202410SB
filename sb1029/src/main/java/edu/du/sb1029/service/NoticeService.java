package edu.du.sb1029.service;

import edu.du.sb1029.entity.Notice;
import edu.du.sb1029.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService implements NoticeServ {
    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public List<Notice> selectNoticeList(){
        return noticeRepository.findAll();
    }

    @Transactional
    @Override
    public void insertNotice(Notice notice) {
        notice = Notice.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                .hitCnt(0)
                .build();

        noticeRepository.save(notice);
    }

    @Transactional
    @Override
    public Notice selectNoticeDetail(int id){
        Notice notice = noticeRepository.findById(id).orElseThrow(()-> new RuntimeException("Notice Not Found"));
        notice.setHitCnt(notice.getHitCnt()+1);
        noticeRepository.save(notice);

        return notice;
    }

    @Transactional
    @Override
    public void updateNotice(Notice notice){
        notice = noticeRepository.findNoticeById(notice.getId());
        notice.setTitle(notice.getTitle());
        notice.setContent(notice.getContent());
        notice.setHitCnt(notice.getHitCnt()+1);
        notice.setUpdatedDatetime(LocalDateTime.now().toString());
        noticeRepository.save(notice);
    }

    @Transactional
    @Override
    public void deleteNotice(int id){
        noticeRepository.deleteById(id);
    }



}
