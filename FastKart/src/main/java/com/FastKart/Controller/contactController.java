package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.contactDao;
import com.FastKart.entities.Contact;
import com.FastKart.services.EmailService;

@Controller
public class contactController {
  
	@Autowired private contactDao cdao;
	@Autowired private EmailService emailService;
	
	@PostMapping("/contact_us")
	public String Contact_us(@ModelAttribute("contact") Contact contact,RedirectAttributes redirAttrs){
		
		String usersendto = contact.getEmail();
		String Usersubject = "Contact Inquiry";
		String Usermessage = ""
				+"<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+"<tr>"
				+"<td bgcolor='#F9FAFC'>"
				+"<div align='center' style='padding: 45px 0;'>"
				+"<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+"<thead>"
				+"<tr>"
				+"<td style='text-align: center;'>"
				+"<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+"<h2 align='center'>Contact Inquiry</h2>"
				+"</td>"
				+"</tr>"
				+"</thead>"
				+"<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='margin: 0; color: #475467;'>Hi,<strong>"+contact.getFname()+" "+contact.getLname()+"</strong></p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'>Thank You for contact us</p>"
				+"</td>"
				+"</tr>"
				+"</tbody>"
				+"<tfoot>"
				+"<tr>"
				+"<td>"
				+"<p align='center'>"
				+"<small style='color:#7a899f;'>&copy;2023 Copyright <a href='http://localhost:8080/' target='_blank' style='text-decoration: none; color: #1f74ca;'>FastKart</a>. All Rights Reserved.</small>"
				+"</p>"
				+"</td>"
				+"</tr>"
				+"</tfoot>"
				+"</table>"
				+"</div>"
				+"</td>"
				+"</tr>"
				+"</table>";

		boolean userEmailSent  = this.emailService.sendEmail(Usersubject, Usermessage, usersendto);
		
		String self = "dhusain4.3.2000@gmail.com";
		String subject = "Contact Inquiry";
		String message = ""
				+"<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+"<tr>"
				+"<td bgcolor='#F9FAFC'>"
				+"<div align='center' style='padding: 45px 0;'>"
				+"<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+"<thead>"
				+"<tr>"
				+"<td style='text-align: center;'>"
				+"<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+"<h2 align='center'>Contact Inquiry</h2>"
				+"</td>"
				+"</tr>"
				+"</thead>"
				+"<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='margin: 0; color: #475467;'>Hi,<strong>Admin</strong></p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Name:</strong> "+contact.getFname() +" "+ contact.getLname()+".</p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Message:</strong> "+contact.getMessage()+".</p>"
				+"</td>"
				+"</tr>"
				+"</tbody>"
				+"<tfoot>"
				+"<tr>"
				+"<td>"
				+"<p align='center'>"
				+"<small style='color:#7a899f;'>&copy;2023 Copyright <a href='http://localhost:8080/' target='_blank' style='text-decoration: none; color: #1f74ca;'>FastKart</a>. All Rights Reserved.</small>"
				+"</p>"
				+"</td>"
				+"</tr>"
				+"</tfoot>"
				+"</table>"
				+"</div>"
				+"</td>"
				+"</tr>"
				+"</table>";
		String to=self;

		boolean adminEmailSent = this.emailService.sendEmail(subject, message, to);

		if (adminEmailSent && userEmailSent) {
			Contact contact_us = cdao.contact_us(contact);
			
			if (contact_us != null) {
				redirAttrs.addFlashAttribute("success", "Contact successfully send");
			} else {
				redirAttrs.addFlashAttribute("error", "Something went wrong");
			}
		} else {
			redirAttrs.addFlashAttribute("error", "Contact failed.");
		}
		
		return "redirect:/contact";
	}
}
