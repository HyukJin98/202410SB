package edu.du.samplep.service;

import edu.du.samplep.entity.Post;
import edu.du.samplep.entity.User;
import edu.du.samplep.repository.PostRepository;
import edu.du.samplep.repository.UserRepository;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PostRepository postRepository;


    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());

        // 빌더 패턴을 사용하여 사용자 정보를 업데이트
        user = User.builder()
                .id(user.getId()) // 기존 ID를 유지
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .password(encodedPassword)  // 암호화된 비밀번호 저장
                .build();

        user = userRepository.save(user);

        // 인증 세션 갱신
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return user;
    }

    // 다른 CRUD 메소드 예시
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Long register(RegisterRequest req) throws DuplicateMemberException {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) {
            throw new DuplicateMemberException("Email already exists: " + req.getEmail());
        }

        User newUser = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder().encode(req.getPassword()))
                .username(req.getName())
                .build();

        userRepository.save(newUser);
        System.out.println("====> Registered User: " + newUser);
        return newUser.getId();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
