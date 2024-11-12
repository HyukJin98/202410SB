package edu.du.samplep.controller;

import edu.du.samplep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;




    @GetMapping("/login")
    public String login() {
        return "/login/login";
    }


    @GetMapping("/login-success")
    public String loginSuccess() {
        return "login-success";  // login-success.html 템플릿을 반환
    }

    @GetMapping("/login-fail")
    public String loginFail() {
        return "login-fail";  // login-success.html 템플릿을 반환
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success";  // login-success.html 템플릿을 반환
    }



    @GetMapping("/basic") // 인덱스 페이지를 위한 메서드
    public String index() {
        return "basic"; // basic.html 뷰 반환
    }



}