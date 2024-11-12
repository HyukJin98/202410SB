package edu.du.samplep;

import edu.du.samplep.entity.Comment;
import edu.du.samplep.entity.Post;
import edu.du.samplep.entity.User;
import edu.du.samplep.repository.CommentRepository;
import edu.du.samplep.repository.PostRepository;
import edu.du.samplep.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SamplePApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void contextLoads() {
        User user = User.builder()
                .username("이혁진")
                .email("dlgurwls321@naver.com")
                .password("1234")
                .build();
        userRepository.save(user);
        System.out.println(user);
    }

    @Test
    void Test2(){

        Long comment = commentRepository.countByPost_Id(1L);
        System.out.println(comment);;
    }


}
