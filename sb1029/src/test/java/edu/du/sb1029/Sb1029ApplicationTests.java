package edu.du.sb1029;

import edu.du.sb1029.entity.Notice;
import edu.du.sb1029.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class Sb1029ApplicationTests {

    @Autowired
    NoticeRepository noticeRepository;

    @Test
    void test() {
        Notice notice = Notice.builder()
                .title("안녕하세요")
                .content("내용입니다")
                .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                .hitCnt(0)
                .build();
        noticeRepository.save(notice);
        System.out.println(notice);
    }

}
