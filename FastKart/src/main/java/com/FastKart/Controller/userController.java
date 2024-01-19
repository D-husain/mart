package com.FastKart.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.User;
import com.FastKart.services.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class userController {

	@Autowired private userDao udao;
	@Autowired private UserRepository userRepository;
	@Autowired private BCryptPasswordEncoder passwordEncoder;
	@Autowired private EmailService emailService;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public userController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	

	@PostMapping("/userRegister")
	public String userRegister(@ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "checkbox", defaultValue = "false") boolean checkbox,
			Model m, HttpSession session, RedirectAttributes redirAttrs) {

		if (!checkbox || result.hasErrors()) {
			redirAttrs.addFlashAttribute("error", "Please fill out all fields and accept terms.");
			return "redirect:/sign-up";
		} 
		else if (!checkbox) {
			redirAttrs.addFlashAttribute("error", "You have not checked terms & condition");
			return "redirect:/sign-up";
		}
		
		String to = user.getEmail();
		String subject = "Register";
		String message = ""
				+ "<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "<tr>"
				+ "<td bgcolor='#F9FAFC'>"
				+ "<div align='center' style='padding:5px 0;'>"
				+ "<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+ "<thead>"
				+ "<tr>"
				+ "<td style='text-align: center;'>"
				+ "<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+ "<h2 align='center'>Thank You</h2>"
				+ "</td>"
				+ "</tr>"
				+ "</thead>"
				+ "<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+ "<tr>"
				+ "<td>"
				+ "<p align='center' style='margin: 0; color: #475467;'>Hey,<strong>"+user.getUsername()+"</strong></p>"
				+ "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>"
				+ "<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'>Thank you for registered.We're so excited to share the latest news and updates about our product with you.If you'd like to learn more, follow us on social media!</p>"
				+ "</td>"
				+ "</tr>"
				+ "</tbody>"
				+ "<tfoot>"
				+ "<tr>"
				+ "<td>"
				+ "<p align='center'>"
				+ "<small style='color:#7a899f;'>&copy;2023 Copyright <a href='http://localhost:8080/' target='_blank' style='text-decoration: none; color: #1f74ca;'>FasrKart</a>. All Rights Reserved.</small>"
				+ "</p>" + "</td>" + "</tr>" + "</tfoot>" + "</table>" + "</div>" + "</td>"
				+ "</tr>" + "</table>";
		
		boolean flag = this.emailService.sendEmail(subject, message, to);
		
		if (flag) {
			user.setRole("user");
			user.setCreated_at(LocalDateTime.now());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User u = udao.userRegister(user);
			
			if (u != null) {
				redirAttrs.addFlashAttribute("success", "Register successfully");
			} else {
				redirAttrs.addFlashAttribute("error", "Something went wrong");
				return "redirect:/sign-up";
			}
		} else {
			redirAttrs.addFlashAttribute("error", "Contact failed.");
			return "redirect:/sign-up";
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
			if (user_name.equals(user.getUsername()) && password.equals(user.getPassword())) {
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
	
	
	
	@PostMapping("/user-change-password")
	public String changePassword(@RequestParam("oldpassword") String oldpassword, RedirectAttributes redirAttrs,
			@RequestParam("newpassword") String newpassword, Principal principal, HttpSession session, Model model) {
		
		if (oldpassword.isEmpty() || newpassword.isEmpty()) {
	        redirAttrs.addFlashAttribute("error", "Password fields cannot be empty.");
	        return "redirect:/dashboard";
	    }
		String name = principal.getName();
		User currentUser = this.userRepository.getUserByUserName(name);

		if (this.passwordEncoder.matches(oldpassword, currentUser.getPassword())) {

			String hashedNewPassword = this.passwordEncoder.encode(newpassword);
			currentUser.setPassword(hashedNewPassword);
			this.userRepository.save(currentUser);
			redirAttrs.addFlashAttribute("success", "Your password has been successfully changed");
			return "redirect:/login";
		} else {
			redirAttrs.addFlashAttribute("error", "Please enter the correct old password");
			return "redirect:/dashboard";
		}
	}
	
	
	
	@PostMapping("/send-otp")
	public String sendotp(@RequestParam("email") String email, HttpSession session,RedirectAttributes redirAttrs) {

		Random random = new Random();
		int otp1 = random.nextInt(10);
		int otp2 = random.nextInt(10);
		int otp3 = random.nextInt(10);
		int otp4 = random.nextInt(10);
		int otp5 = random.nextInt(10);
		int otp6 = random.nextInt(10);
		
		int generatedOTP = otp1 * 100000 + otp2 * 10000 + otp3 * 1000 + otp4 * 100 + otp5 * 10 + otp6;

		String subject = "Forgot Password";
		String message = ""
				+ "<html>"
				+ "<head>"
				+ "    <link rel='preconnect' href='https://fonts.googleapis.com/'>"
				+ "    <link href='https://fonts.googleapis.com/css2?family=Public+Sans:wght@100;200;300;400;500;600;700;800;900&amp;display=swap' rel='stylesheet'>"
				+ "    <link href='https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@200;300;400;600;700;800;900&amp;display=swap' rel='stylesheet'>"
				
				+ "    <style type='text/css'>"
				+ "        body { text-align: center; margin: 0 auto; width: 650px; font-family: 'Public Sans', sans-serif; background-color: #e2e2e2; display: block; } ul { margin: 0; padding: 0; } li { display: inline-block; text-decoration: unset;}  a { text-decoration: none; }" + "" + "        h5 { margin: 10px; color: #777; } .text-center { text-align: center } .header-menu ul li+li {margin-left: 20px; } .header-menu ul li a { font-size: 14px; color: #252525; font-weight: 500;  } .password-button { background-color: #0DA487; border: none; color: #fff; padding: 14px 26px; font-size: 18px; border-radius: 6px; font-weight: 600; } .footer-table { position: relative; } .footer-table::before { position: absolute; content: ''; background-image: url(images/footer-left.svg); background-position: top right; top: 0; left: -71%; width: 100%; height: 100%; background-repeat: no-repeat; z-index: -1;  background-size: contain; opacity: 0.3; } .footer-table::after { position: absolute; content: ''; background-image: url(images/footer-right.svg); background-position: top right; top: 0; right: 0; width: 100%; height: 100%; background-repeat: no-repeat; z-index: -1; background-size: contain; opacity: 0.3; } .theme-color { color: #0DA487; }"
				+ "    </style>"
				+ "</head>"
				
				+ "<body style='margin: 20px auto;'>"
				+ "    <table align='center' border='0' cellpadding='0' cellspacing='0' style='background-color: white; width: 100%; box-shadow: 0px 0px 14px -4px rgba(0, 0, 0, 0.2705882353);-webkit-box-shadow: 0px 0px 14px -4px rgba(0, 0, 0, 0.2705882353);'>"
				+ "        <tbody>"
				+ "            <tr>"
				+ "                <td>"
				+ "                    <table class='header-table' align='center' border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "                        <tr class='header' style='background-color: #f7f7f7;display: flex;align-items: center;justify-content: space-between;width: 100%;'>"
				+ "                            <td class='header-logo' style='padding: 10px 32px;'>"
				+ "                                <a href='https://themes.pixelstrap.com/fastkart/front-end/index.html' style='display: block; text-align: left;'>"
				+ "                                    <img src='https://themes.pixelstrap.com/fastkart/email-templete/images/logo.png' class='main-logo' alt='logo'>"
				+ "                                </a>"
				+ "                            </td>"
				                           
				+ "                        </tr>"
				+ "                    </table>"
				+ ""
				+ "                    <table class='contant-table' style='margin-top: 40px;' align='center' border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "                        <thead>"
				+ "                            <tr>"
				+ "                                <td>"
				+ "                                    <img src='https://themes.pixelstrap.com/fastkart/email-templete/images/reset.png' alt=''>"
				+ "                                </td>"
				+ "                            </tr>"
				+ "                        </thead>"
				+ "                    </table>"
				+ ""
				+ "                    <table class='contant-table' style='margin-top: 40px;' align='center' border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "                        <thead>"
				+ "                            <tr style='display: block;'>"
				+ "                                <td style='display: block;'>"
				+ "                                    <h3 style='font-weight: 700; font-size: 20px; margin: 0;'>Reset Password</h3>"
				+ "                                </td>"
				+ ""
				+ "                                <td style='display: block;'>"
				+ "                                    <h3 style='font-weight: 700; font-size: 20px; margin: 0;color: #939393;margin-top: 15px;'>"
				+ "                                        Hi Julie Griffin,</h3>"
				+ "                                </td>"
				+ ""
				+ "                                <td>"
				+ "                                    <p style='font-size: 17px;font-weight: 600;width: 74%;margin: 8px auto 0;line-height: 1.5;color: #939393;'>"
				+ "                                        We’re Sending you this email because You requested a password reset. click on this link to create a new password:</p>"
				+ "                                </td>"
				+ "                            </tr>"
				+ "                        </thead>"
				+ "                    </table>"
				+ ""
				+ "                    <table class='button-table' style='margin-top: 27px;' align='center' border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "                        <thead>"
				+ "                            <tr style='display: block;'>"
				+ "                                <td style='display: block;'>"
				+ "                                    <button class='password-button'>"+ otp1 + otp2 +otp3 +otp4 +otp5 +otp6 +"</button>"
				+ "                                </td>"
				+ "                            </tr>"
				+ "                        </thead>"
				+ "                    </table>"
				+ ""
				+ "                    <table class='contant-table' style='margin-top: 27px;' align='center' border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "                        <thead>"
				+ "                            <tr style='display: block;'>"
				+ "                                <td style='display: block;'>"
				+ "                                    <p style='font-size: 17px;font-weight: 600;width: 74%;margin: 8px auto 0;line-height: 1.5;color: #939393;'>"
				+ "                                        If you didn’t request a password reset, you can ignore this email. your password will not be changed.</p>"
				+ "                                </td>"
				+ "                            </tr>"
				+ "                        </thead>"
				+ "                    </table>"
				+ ""
				+ "                    <table class='text-center footer-table' align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='background-color: #282834; color: white; padding: 24px; overflow: hidden; z-index: 0; margin-top: 30px;'>"
				+ "                        <tr>"
				+ "                            <td>"
				+ "                                <table border='0' cellpadding='0' cellspacing='0' class='footer-social-icon text-center' align='center' style='margin: 8px auto 11px;'>"
				+ "                                    <tr>"
				+ "                                        <td>"
				+ "                                            <h4 style='font-size: 19px; font-weight: 700; margin: 0;'>Shop For <span class='theme-color'>Fastkart</span></h4>"
				+ "                                        </td>"
				+ "                                    </tr>"
				+ "                                </table>"
				+ "                                <table border='0' cellpadding='0' cellspacing='0' class='footer-social-icon text-center' align='center' style='margin: 8px auto 20px;'>"
				+ "                                    <tr>"
				+ "                                        <td>"
				+ "                                            <a href='javascript:void(0)' style='font-size: 14px; font-weight: 600; color: #fff; text-decoration: underline; text-transform: capitalize;'>Contact"
				+ "                                                Us</a>"
				+ "                                        </td>"
				+ "                                        <td>"
				+ "                                            <a href='javascript:void(0)' style='font-size: 14px; font-weight: 600; color: #fff; text-decoration: underline; text-transform: capitalize; margin-left: 20px;'>unsubscribe</a>"
				+ "                                        </td>"
				+ "                                        <td>"
				+ "                                            <a href='javascript:void(0)' style='font-size: 14px; font-weight: 600; color: #fff; text-decoration: underline; text-transform: capitalize; margin-left: 20px;'>privacy"
				+ "                                                Policy</a>"
				+ "                                        </td>"
				+ "                                    </tr>"
				+ "                                </table>"
				+ "                                <table border='0' cellpadding='0' cellspacing='0' class='footer-social-icon text-center' align='center' style='margin: 23px auto;'>"
				+ "                                    <tr>"
				+ "                                        <td>"
				+ "                                            <a href='www.facebook.html'>"
				+ "                                                <img src='images/fb.png' style='font-size: 25px; margin: 0 18px 0 0;width: 22px;filter: invert(1);' alt=''>"
				+ "                                            </a>"
				+ "                                        </td>"
				+ "                                        <td>"
				+ "                                            <a href='www.twitter.html'>"
				+ "                                                <img src='images/twitter.png' style='font-size: 25px; margin: 0 18px 0 0;width: 22px;filter: invert(1);' alt=''>"
				+ "                                            </a>"
				+ "                                        </td>"
				+ "                                        <td>"
				+ "                                            <a href='www.instagram.html'>"
				+ "                                                <img src='images/insta.png' style='font-size: 25px; margin: 0 18px 0 0;width: 22px;filter: invert(1);' alt=''>"
				+ "                                            </a>"
				+ "                                        </td>"
				+ "                                        <td>"
				+ "                                            <a href='www.pinterest.html'>"
				+ "                                                <img src='images/pinterest.png' style='font-size: 25px; margin: 0 18px 0 0;width: 22px;filter: invert(1);' alt=''>"
				+ "                                            </a>"
				+ "                                        </td>"
				+ "                                    </tr>"
				+ "                                </table>"
				+ "                                <table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+ "                                    <tr>"
				+ "                                        <td>"
				+ "                                            <h5 style='font-size: 13px; text-transform: uppercase; margin: 0; color:#ddd; letter-spacing:1px; font-weight: 500;'>Want to change how you receive these emails?"
				+ "                                            </h5>"
				+ "                                            <h5 style='font-size: 13px; text-transform: uppercase; margin: 10px 0 0; color:#ddd; letter-spacing:1px; font-weight: 500;'>"
				+ "                                             2021-22 copy right by themeforest powerd by pixelstrap</h5>"
				+ "                                        </td>"
				+ "                                    </tr>"
				+ "                                </table>"
				+ "                            </td>"
				+ "                        </tr>"
				+ "                    </table>"
				+ "                </td>"
				+ "            </tr>"
				+ "        </tbody>"
				+ "    </table>"
				+ "</body>"
				+ "</html>";
		String to = email;

		User findemail = userRepository.findByEmail(email);

		if (findemail != null) {
			boolean flag = this.emailService.sendEmail(subject, message, to);

			if (flag) {
				session.setAttribute("otp", generatedOTP);
				System.out.println("generatedOTP"+generatedOTP);
				session.setAttribute("email", email);
				redirAttrs.addFlashAttribute("success", "OTP is sent to your email id");
				return "redirect:/varify-otp";
			} else {
				return "redirect:/forgot";
			}
		} else {
			redirAttrs.addFlashAttribute("error", "Please enter registered email");
			return "redirect:/forgot";
		}
	}
	
	
	@PostMapping("/varify-otp")
	public String varifyotp(@RequestParam("otp1") String otp1, @RequestParam("otp2") String otp2,
			@RequestParam("otp3") String otp3, @RequestParam("otp4") String otp4, @RequestParam("otp5") String otp5,
			@RequestParam("otp6") String otp6, HttpSession session, RedirectAttributes redirAttrs) {

		if (!otp1.isEmpty() && !otp2.isEmpty() && !otp3.isEmpty() && !otp4.isEmpty() && !otp5.isEmpty() && !otp6.isEmpty()) {
			try {
				int otp = Integer.parseInt(otp1 + otp2 + otp3 + otp4 + otp5 + otp6);
				
				if (otp >= 0 && otp <= 999999) {
					int myotp = (int) session.getAttribute("otp");
					if (myotp == otp) {
						redirAttrs.addFlashAttribute("success", "Email verified successfully");
						   session.removeAttribute("otp");
						return "redirect:/reset_password";
					} else {
						redirAttrs.addFlashAttribute("error", "Please enter a valid OTP");
						return "redirect:/varify-otp";
					}
				} else {
					redirAttrs.addFlashAttribute("error", "Invalid OTP");
					return "redirect:/varify-otp";
				}
			} catch (NumberFormatException e) {
				redirAttrs.addFlashAttribute("error", "Invalid OTP format");
				return "redirect:/varify-otp";
			}
		} else {
			redirAttrs.addFlashAttribute("error", "Please enter the complete OTP");
			return "redirect:/varify-otp";
		}
	}
	
	
	
	@PostMapping("/reset_password")
	public String reset_password(@RequestParam("newpassword") String newpassword,
			@RequestParam("confirmPassword") String confirmPassword,HttpSession session,RedirectAttributes redirAttrs) {
		String email = (String) session.getAttribute("email");
		
		if (newpassword.isEmpty() || confirmPassword.isEmpty()) {
	        redirAttrs.addFlashAttribute("error", "Password fields cannot be empty.");
	        return "redirect:/reset_password";
	    }else if (!newpassword.equals(confirmPassword)) {
	        redirAttrs.addFlashAttribute("error", "Passwords do not match.");
	        return "redirect:/reset_password";
	    }
		
		User user = this.userRepository.getUserByUserName(email);

		if (user != null) {
			
			if (!this.bCryptPasswordEncoder.matches(newpassword, user.getPassword())) {
	            redirAttrs.addFlashAttribute("error", "Incorrect current password.");
	            return "redirect:/reset_password";
	        }
			
			user.setPassword(this.bCryptPasswordEncoder.encode(newpassword));
			this.userRepository.save(user);
			
			redirAttrs.addFlashAttribute("success", "Password reset successfully.");
			return "redirect:/login";
		} else {
			return "redirect:/reset-password";
		}
	}
	
	

}
