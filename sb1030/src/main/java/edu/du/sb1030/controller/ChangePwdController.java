package edu.du.sb1030.controller;

import javax.servlet.http.HttpSession;

import edu.du.sb1030.spring.AuthInfo;
import edu.du.sb1030.spring.ChangePasswordService;
import edu.du.sb1030.spring.WrongIdPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {

	@Autowired
	private ChangePasswordService changePasswordService;



	@GetMapping
	public String form(
			@ModelAttribute("command") ChangePwdCommand pwdCmd,Model model) {
		model.addAttribute("changePasswordCommand", pwdCmd);
		return "edit/changePwdForm";
	}

	@PostMapping
	public String submit(
			@ModelAttribute("command") ChangePwdCommand pwdCmd,
			Errors errors,
			HttpSession session, Model model) {
		new ChangePwdCommandValidator().validate(pwdCmd, errors);
		model.addAttribute("changePasswordCommand", pwdCmd);
		if (errors.hasErrors()) {
			return "edit/changePwdForm";
		}
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		try {
			changePasswordService.changePassword(
					authInfo.getEmail(),
					pwdCmd.getCurrentPassword(),
					pwdCmd.getNewPassword());
			return "edit/changedPwd";
		} catch (WrongIdPasswordException e) {
			errors.rejectValue("currentPassword", "notMatching");
			return "edit/changePwdForm";
		}
	}
}
