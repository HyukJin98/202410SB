package edu.du.samplep.controller;

import edu.du.samplep.entity.Comment;
import edu.du.samplep.entity.Post;
import edu.du.samplep.entity.User;
import edu.du.samplep.service.CommentService;
import edu.du.samplep.service.PostService;
import edu.du.samplep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final PostService postService;

    private final UserService userService;

    @PostMapping("/comments/add")
    public String addComment(@RequestParam Long id, @RequestParam String content, RedirectAttributes redirectAttributes) {
        System.out.println("게시글 id :" + id);
        if (isAuthenticated()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(username);
            Post post = postService.getPostById(id).orElse(null);

            if (post != null) {
                Comment comment = new Comment();
                comment.setContent(content);
                comment.setPost(post);
                comment.setUser(user);  // 로그인된 사용자 정보 설정

                commentService.saveComment(comment);  // 댓글 저장
                redirectAttributes.addFlashAttribute("successMessage", "댓글이 작성되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "게시글을 찾을 수 없습니다.");
            }

            return "redirect:/posts/"+id;  // 작성한 게시글의 상세 페이지로 리다이렉트
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "로그인이 필요합니다.");
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        }
    }

    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId,
                                @RequestParam("_method") String method,
                                @RequestParam("postId") Long postId, // postId 추가
                                RedirectAttributes redirectAttributes) {
        System.out.println("댓글  :" + commentId);

        // 댓글 삭제 처리
        if ("delete".equals(method)) {
            Optional<Comment> comment = commentService.getCommentById(commentId);  // 댓글 조회

            if (comment.isPresent()) {
                // 현재 로그인한 사용자 이름 가져오기
                String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

                // 댓글의 작성자 확인
                String commentAuthor = comment.get().getUser().getUsername();

                // 현재 사용자와 댓글 작성자 비교
                if (commentAuthor.equals(currentUser)) {
                    commentService.deleteComment(commentId);  // 작성자가 맞으면 댓글 삭제
                    redirectAttributes.addFlashAttribute("successMessage", "댓글이 삭제되었습니다.");
                } else {
                    redirectAttributes.addFlashAttribute("warningMessage", "작성자만 댓글을 삭제할 수 있습니다.");
                }
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "댓글을 찾을 수 없습니다.");
            }
        }
        // postId를 사용하여 리다이렉트 처리
        return "redirect:/posts/" + postId;  // 댓글이 속한 게시글 상세 페이지로 리다이렉트
    }

    @GetMapping("/comments/{commentId}/update")
    public String editCommentForm(@PathVariable Long commentId, Model model,@RequestParam("postId") Long postId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (comment.isPresent()) {
            model.addAttribute("comment", comment);
            return "/redirect:/posts/"+postId; // 댓글 수정 뷰로 이동
        }
        return "redirect:/"; // 댓글을 찾을 수 없는 경우 홈으로 리디렉션
    }

    @PostMapping("/comments/{commentId}/update")
    public String updateComment(@PathVariable Long commentId,
                                @RequestParam("content") String newContent,
                                @RequestParam("postId") Long postId,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        Optional<Comment> comment1 = commentService.updateComment(commentId,newContent);



        // 수정 후 해당 게시글의 상세 페이지로 리다이렉트
        return "redirect:/posts/"+postId;  // {postId}는 수정된 댓글이 속한 게시글의 ID
    }




    // 로그인 여부 확인
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getName().equals("anonymousUser"));
    }
}