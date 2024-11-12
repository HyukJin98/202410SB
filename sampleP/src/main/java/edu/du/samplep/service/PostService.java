package edu.du.samplep.service;

import edu.du.samplep.entity.Post;
import edu.du.samplep.repository.PostRepository;
import edu.du.samplep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));
    }

    public Optional<Post> getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(p -> {
            p.increaseViews();  // 조회수 증가
            postRepository.save(p);
        });
        return post;

    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post updatePost(Long id, Post postDetails) {
        // 먼저 id로 기존 게시글을 찾아옵니다.
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        // 찾은 게시글의 속성들을 업데이트합니다.
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        // 다른 필드들에 대해서도 동일하게 업데이트할 수 있습니다.

        // 업데이트된 게시글을 저장하여 반환합니다.
        return postRepository.save(post);
    }

    public List<Post> getTopViewedPosts() {
        return postRepository.findTop5ByOrderByViewsDesc();
    }

}
