package edu.du.samplep.repository;

import edu.du.samplep.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 조회수를 기준으로 오름차순 정렬된 모든 게시글 조회
    List<Post> findTop5ByOrderByViewsDesc();;

}
