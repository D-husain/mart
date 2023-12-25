package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.addressDao;
import com.FastKart.entities.Address;

import jakarta.websocket.server.PathParam;

@Controller
public class addressController {
	
	@Autowired
	private addressDao addao;

//=========================================== ADD ADDRESS METHOD IS USER IS LOGIN ===============================================================
	@PostMapping("/addAddress")
	public String addAddress(@ModelAttribute Address address, Model m,Principal principal, RedirectAttributes reAttributes) {
		
		if(principal!=null) {
		Address addAddress = addao.addAddress(address, principal);
		
		reAttributes.addFlashAttribute("error", "Address successfully added");
		return  "redirect:/checkOut1";
	}
		else {
			return null;
		}

}
	
	
	@GetMapping("/deleteAddress/{id}")
	public String deleteAddress(@PathVariable("id") int id, Model m) {
		
		addao.deleteAddress(id);
		return "redirect:/checkOut1";
		
	
	}
	
	
	
}
	