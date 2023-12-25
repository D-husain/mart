package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.FastKart.Dao.contactDao;
import com.FastKart.entities.Contact;

@Controller
public class contactController {
  
	@Autowired
	private contactDao cdao;
	
	@PostMapping("/contact_us")
	public String Contact_us(@ModelAttribute Contact contact){
		
		Contact contact_us = cdao.contact_us(contact);
		
		return "redirect:/contact";
		
	}
}
