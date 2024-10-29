package edu.du.sb1029;

import edu.du.sb1029.entity.Notice;
import edu.du.sb1029.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class Sb1029Application {

    @Autowired
    NoticeRepository noticeRepository;

    public static void main(String[] args) {
        SpringApplication.run(Sb1029Application.class, args);
    }


    @PostConstruct
    public void init() {
        IntStream.range(0,10).forEach(i -> {
           Notice notice = Notice.builder()
                   .title("Title " + i)
                   .content("Content " + i)
                   .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                   .hitCnt(0)
                   .build();
           noticeRepository.save(notice);
        });
    }
}
