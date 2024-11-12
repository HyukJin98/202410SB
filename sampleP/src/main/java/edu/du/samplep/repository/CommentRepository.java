package edu.du.samplep.repository;

import edu.du.samplep.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByPost_Id(Long postId);

    List<Comment> findByPostId(Long postId);


}
