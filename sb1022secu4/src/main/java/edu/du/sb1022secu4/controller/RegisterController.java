package edu.du.sb1022secu4.controller;


import edu.du.sb1022secu4.entity.Member;
import edu.du.sb1022secu4.service.MemberRegisterService;
import edu.du.sb1022secu4.service.RegisterRequest;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegisterController {

	@Autowired
	MemberRegisterService memberRegisterService;



	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}
	


	@GetMapping("/main")
	public String main() {
		return "main";
	}


	@PostMapping("/register/step2")
	public String handleStep2(
			@RequestParam(value = "agree", defaultValue = "false") Boolean agree,
			Model model) {
		if (!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}

	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}

	@PostMapping("/register/step3")
	public String handleStep3(@ModelAttribute("registerRequest")Member member) {
		try {
			memberRegisterService.register(member);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			return "register/step2";
		}
	}

}
