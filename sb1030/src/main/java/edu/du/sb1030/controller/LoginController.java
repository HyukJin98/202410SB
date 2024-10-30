package edu.du.sb1030.controller;

import javax.servlet.http.HttpSession;

import edu.du.sb1030.spring.AuthInfo;
import edu.du.sb1030.spring.AuthService;
import edu.du.sb1030.spring.WrongIdPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AuthService authService;

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
	@GetMapping
	public String form(LoginCommand loginCommand, Model model) {
		model.addAttribute("loginCommand", loginCommand);
		return "login/loginForm";
	}
	
	@PostMapping
	public String submit(LoginCommand loginCommand, Errors errors, HttpSession session, Model model) {
		new LoginCommandValidator().validate(loginCommand, errors);
		model.addAttribute("loginCommand", loginCommand);
		if(errors.hasErrors()) {
			return "login/loginForm";
		}
		try {
			AuthInfo authInfo = authService.authenticate(
					loginCommand.getEmail(), 
					loginCommand.getPassword());
			session.setAttribute("authInfo", authInfo);
			return "login/loginSuccess";
		}catch (WrongIdPasswordException e) {
			errors.reject("idPasswordNotMatching");
			return "login/loginForm";
		}
	}
}
