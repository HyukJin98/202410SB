package edu.du.samplep.controller;

import edu.du.samplep.entity.User;
import edu.du.samplep.repository.UserRepository;
import edu.du.samplep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // 사용자 프로필 페이지 보여주기
    @GetMapping("/user/profile")
    public String showUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User user = userRepository.findByUsername(currentUsername);
        model.addAttribute("user", user);
        return "/user/user-profile";  // 프로필 페이지
    }

    // 사용자 정보 수정 폼
    @GetMapping("/user/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);

        return "/user/user-update-form";  // 수정 폼 페이지
    }

    // 사용자 정보 수정 처리
    @PostMapping("/user/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id,
                                             @Valid @ModelAttribute("user") User userDetails,
                                             BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("유효성 검사 실패");  // 유효성 검사 실패 시 오류 메시지 반환
        }

        // 기존 사용자 정보 로드
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // 사용자 정보 업데이트
        userService.updateUser(id, userDetails);

        // 사용자 정보를 업데이트 후, SecurityContext에 로그인 정보 갱신
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok("정보 수정 완료");  // 성공 시 메시지 반환
    }

    // WebDataBinder에 유효성 검사기 추가
    @InitBinder
    protected void InitBinder(WebDataBinder binder) {
        binder.addValidators(new UserUpdateValidator());
    }
}