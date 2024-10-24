package edu.du.sb1021_3;

import edu.du.sb1021_3.entity.Member;
import edu.du.sb1021_3.repository.MemberRepository;
import edu.du.sb1021_3.spring.MemberDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class Sb10212ApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberDao memberDao;

    @Test
    void 입력_테스트() {
        Member member = Member.builder()
                .name("홍길동")
                .password("1234")
                .email("hong@korea.com")
                .regdate(LocalDateTime.now())
                .build();
        System.out.println(memberRepository.save(member));

        Member members = memberDao.selectByEmail("hong@korea.com").get();
        System.out.println(members);
    }

}
