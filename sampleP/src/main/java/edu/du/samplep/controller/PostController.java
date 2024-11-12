package edu.du.samplep.controller;

import edu.du.samplep.entity.Comment;
import edu.du.samplep.entity.Post;
import edu.du.samplep.entity.User;
import edu.du.samplep.service.CommentService;
import edu.du.samplep.service.PostService;
import edu.du.samplep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    // 모든 게시글 목록 조회
    @GetMapping("/")
    public String getAllPosts(Model model) {


        model.addAttribute("commentService", commentService);
        model.addAttribute("posts", postService.getAllPosts());
        return "basic";  // 게시글 목록을 보여주는 메인 페이지
    }

    @GetMapping("/posts/bestPost")
    public String getBestPost(Model model) {
        model.addAttribute("posts",postService.getTopViewedPosts());
        return "/posts/bestPost";
    }


    // 게시글 작성 폼 (로그인한 사용자만 접근 가능)
    @GetMapping("/posts/new")
    public String createPostForm(Model model, RedirectAttributes redirectAttributes) {
        // 로그인 여부 체크
        if (isAuthenticated()) {
            model.addAttribute("post", new Post());
            return "basic";  // 게시글 작성 폼을 포함한 메인 페이지 (모달로 작성 가능)
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "로그인이 필요합니다.");
            return "redirect:/";  // 로그인 페이지로 리다이렉트
        }
    }

    // 게시글 저장
    @PostMapping("/posts/new")
    public String createPost(@ModelAttribute Post post,RedirectAttributes redirectAttributes) {
        if (isAuthenticated()) {
            // 현재 로그인한 사용자 정보 가져오기
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(username);

            // 게시글의 작성자 설정
            post.setUser(user);

            postService.savePost(post);

            return "redirect:/";  // 게시글 작성 후 목록 페이지로 리다이렉트
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "로그인이 필요합니다.");
            return "redirect:/";  // 로그인되지 않은 경우 로그인 페이지로 이동
        }
    }


    // 게시글 상세보기
    @GetMapping("/posts/{id}")
    public String getPostDetail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        if (isAuthenticated()) { // 로그인 여부 확인
            Optional<Post> post = postService.getPostById(id);
            if (post.isPresent()) {
                model.addAttribute("post", post.get());
                model.addAttribute("comments", commentService.getCommentsByPostId(id));
                model.addAttribute("postId", id);
                return "posts/detail";
            }
            else {
                redirectAttributes.addFlashAttribute("warningMessage", "게시글을 찾을 수 없습니다.");
                return "redirect:/"; // 게시글을 찾을 수 없을 때 리다이렉트
            }
        }
        else {
            redirectAttributes.addFlashAttribute("warningMessage", "로그인이 필요합니다.");
            return "redirect:/"; // 로그인 페이지로 리다이렉트
        }
    }

    // 게시글 삭제 (작성자만 삭제 가능)
    @PostMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id, @RequestParam("_method") String method,RedirectAttributes redirectAttributes) {
        if ("delete".equals(method)) {
            Optional<Post> post = postService.getPostById(id);
            if (post.isPresent()) {
                // 현재 로그인한 사용자 이름 가져오기
                String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

                // 게시글의 작성자 확인
                String postAuthor = post.get().getUser().getUsername();

                // 현재 사용자와 작성자 비교
                if (postAuthor.equals(currentUser)) {
                    postService.deletePost(id);
                    redirectAttributes.addFlashAttribute("warningMessage","성공적으로 삭제되었습니다");
                    return "redirect:/";// 작성자가 맞으면 게시글 삭제
                }else{
                    redirectAttributes.addFlashAttribute("warningMessage","작성자만 삭제할수 있습니다");
                    return "redirect:/";
                }

            }
        }

        return "redirect:/";  // 삭제 후 게시글 목록 페이지로 리다이렉트
    }



    @GetMapping("/posts/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            // 게시글 작성자의 username 가져오기
            String postAuthor = post.get().getUser().getUsername();

            // 게시글 작성자만 수정 가능
            if (postAuthor.equals(currentUser)) {
                model.addAttribute("post", post.get());
                return "posts/edit";  // 수정 폼을 제공하는 뷰로 리턴
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "작성자만 수정할 수 있습니다.");
                return "redirect:/";  // 작성자가 아니면 리다이렉트
            }
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "게시글을 찾을 수 없습니다.");
            return "redirect:/";  // 게시글을 찾을 수 없으면 리다이렉트
        }
    }

    // 게시글 업데이트 (폼 제출 후)
    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        Optional<Post> existingPost = postService.getPostById(id);
        if (existingPost.isPresent()) {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            // 게시글 작성자의 username 가져오기
            String postAuthor = existingPost.get().getUser().getUsername();

            // 게시글 작성자만 수정 가능
            if (postAuthor.equals(currentUser)) {
                // 기존 게시글의 user 설정 유지
                post.setUser(existingPost.get().getUser());

                postService.updatePost(id, post);  // 기존 게시글을 업데이트
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 성공적으로 업데이트되었습니다.");
                return "redirect:/posts/" + id;  // 업데이트 후 게시글 상세 페이지로 리다이렉트
            } else {
                redirectAttributes.addFlashAttribute("warningMessage", "작성자만 수정할 수 있습니다.");
                return "redirect:/";  // 작성자가 아니면 리다이렉트
            }
        } else {
            redirectAttributes.addFlashAttribute("warningMessage", "게시글을 찾을 수 없습니다.");
            return "redirect:/";  // 게시글을 찾을 수 없으면 리다이렉트
        }
    }

    // 로그인 여부 확인
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getName().equals("anonymousUser"));
    }

}