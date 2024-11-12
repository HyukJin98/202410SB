package edu.du.samplep.service;

import edu.du.samplep.entity.Comment;
import edu.du.samplep.entity.Post;
import edu.du.samplep.entity.User;
import edu.du.samplep.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public long getCountComments(Long postId) {
        return commentRepository.countByPost_Id(postId);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }


    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);  // 댓글 삭제
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    // 댓글 수정
    @Transactional
    public Optional<Comment> updateComment(Long commentId, String newContent) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment existingComment = comment.get();
            existingComment.setContent(newContent);  // 새로운 내용으로 수정
            commentRepository.save(existingComment);  // 수정된 댓글 저장
            return Optional.of(existingComment);  // 수정된 댓글 반환
        } else {
            return Optional.empty();  // 댓글이 존재하지 않으면 빈 Optional 반환
        }
    }
}