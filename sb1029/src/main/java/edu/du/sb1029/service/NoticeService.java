package edu.du.sb1029.service;

import edu.du.sb1029.entity.Notice;
import edu.du.sb1029.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
        Notice existingNotice = noticeRepository.findById(notice.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공지를 찾을 수 없습니다. id=" + notice.getId()));

        // 빌더 패턴을 사용하여 기존 데이터를 새로 업데이트된 데이터로 변경
        Notice updatedNotice = Notice.builder()
                .id(existingNotice.getId()) // ID는 기존 ID 유지
                .title(notice.getTitle()) // 변경할 필드
                .content(notice.getContent()) // 변경할 필드
                .hitCnt(existingNotice.getHitCnt()) // 조회수는 그대로 유지
                .createdDatetime(existingNotice.getCreatedDatetime()) // 생성일시 그대로 유지
                .build();

        noticeRepository.save(updatedNotice);
    }

    @Transactional
    @Override
    public void deleteNotice(int id){
        noticeRepository.deleteById(id);
    }



}
