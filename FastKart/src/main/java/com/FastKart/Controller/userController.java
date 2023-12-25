package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.userDao;
import com.FastKart.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class userController {

	@Autowired
	private userDao udao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/userRegister")
	public String userRegister(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam(value = "checkbox", defaultValue = "false") boolean checkbox, Model m,HttpSession session,RedirectAttributes redirAttrs) {

		if (result.hasErrors()) {

			System.out.println(result);
			return "register";
		}

		// if checkbox is not checked by user than this if condition is executed
		if (!checkbox) {

			System.out.println("you have not checked terms & condition");
			return "register";

		}

		user.setRole("user");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User u = udao.userRegister(user);

		
		 if (u != null) {
			 System.out.println("User registered successfully: " + u.getName());
			 redirAttrs.addFlashAttribute("success",  "Register successfully"); 
			 }
		      else {
		 System.out.println("Failed to register user");
		 
		 redirAttrs.addFlashAttribute("error", "Something went wrong"); }
		
		return "redirect:/login";
	}
}
