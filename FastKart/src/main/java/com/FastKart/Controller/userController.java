package com.FastKart.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.addressDao;
import com.FastKart.Dao.userDao;
import com.FastKart.entities.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class userController {

	@Autowired private userDao udao;
	@Autowired private addressDao addresdao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@PostMapping("/userRegister")
	public String userRegister(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "checkbox", defaultValue = "false") boolean checkbox,
			Model m, HttpSession session, RedirectAttributes redirAttrs) {

		if (result.hasErrors()) {

			System.out.println(result);
			return "redirect:/sign-up";
		}

		// if checkbox is not checked by user than this if condition is executed
		if (!checkbox) {

			System.out.println("you have not checked terms & condition");
			return "redirect:/sign-up";

		}

		user.setRole("user");
		user.setCreated_at(LocalDateTime.now());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User u = udao.userRegister(user);

		if (u != null) {
			System.out.println("User registered successfully: " + u.getName());
			redirAttrs.addFlashAttribute("success", "Register successfully");
		} else {
			System.out.println("Failed to register user");

			redirAttrs.addFlashAttribute("error", "Something went wrong");
		}

		return "redirect:/login";
	}
	
	
	@PostMapping("/user/login")
	public String userlogin(@RequestParam("username") String user_name, @RequestParam("password") String password,
			HttpServletRequest request, Model model, HttpSession session, RedirectAttributes redirAttrs) {
		List<User> list = udao.ShowAllUser();

		String page_move = "redirect:/login";
		boolean loggedIn = false;

		for (User user : list) {
			if (user_name.equals(user.getName()) && password.equals(user.getPassword())) {
				session.setAttribute("user", user);
				page_move = "redirect:/dashboard";
				loggedIn = true;
				break;
			}
		}

		if (!loggedIn) {
			redirAttrs.addFlashAttribute("error", "Login failed. Invalid username or password.");
		}
		return page_move;
	}
	
}
