package com.FastKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FastKart.Dao.addressDao;
import com.FastKart.Dao.checkoutDao;
import com.FastKart.entities.Address;

import ch.qos.logback.core.model.Model;

@Controller
public class CheckOutController {

	
	@Autowired
	private checkoutDao chdao;
	
	@Autowired
	private addressDao adao;
	 
	@PostMapping("/checkOut")
	public String checkOut(@RequestParam("address_id") int id,Model m) {
		
		Address addressById = adao.getAddressById(id);
		
		chdao.saveSelectedAddress(addressById);
		
		return "redirect:/comfirmOrder";
		
		
	}
}
