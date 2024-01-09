package com.FastKart.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.FastKart.Dao.addressDao;
import com.FastKart.entities.Address;

@Controller
public class addressController {

	@Autowired
	private addressDao addao;

	@PostMapping("/user-add-address")
	public String addAddress(@ModelAttribute("Address") Address address, Model m, Principal principal,RedirectAttributes reAttributes) {

		if (principal != null) {
			Address addAddress = addao.addAddress(address, principal);

			if (addAddress != null) {
				reAttributes.addFlashAttribute("error", "Address successfully added");
			} else {
				reAttributes.addFlashAttribute("error", "Something went wrong");
			}
			return "redirect:/dashboard";

		} else {
			return null;
		}

	}

	@PostMapping("/api-user-add-address")
    public ResponseEntity<String> addAddress(@RequestBody Address address, Principal principal, RedirectAttributes reAttributes) {
        if (principal != null) {
            Address addedAddress = addao.addAddress(address, principal);

            if (addedAddress != null) {
                reAttributes.addFlashAttribute("error", "Address successfully added");
                return ResponseEntity.status(HttpStatus.OK).body("Address successfully added");
            } else {
                reAttributes.addFlashAttribute("error", "Something went wrong");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
	
	@GetMapping("/deleteAddress/{id}")
	public String deleteAddress(@PathVariable("id") int id, Model m) {
		addao.deleteAddress(id);
		return "redirect:/dashboard";

	}

}
