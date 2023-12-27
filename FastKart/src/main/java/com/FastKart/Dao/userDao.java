package com.FastKart.Dao;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.User;

@Service
public class userDao {

	@Autowired
	private UserRepository userRepository;
	
	
	@ModelAttribute("loggedInUser")
	public User getLoggedInUser(Principal principal) {
		if (principal != null) {
			String username = principal.getName();
			return userRepository.getUserByUserName(username);
		}
		return null;
	}
	
	
	

//========================================= method for check if user is login or not====================================================================
	 public boolean isUserLoggedIn(Principal principal) {
	       
		 if (principal != null && principal.getName() != null && !principal.getName().isEmpty()) {
		        String name = principal.getName();
		        User user = userRepository.getUserByUserName(name);
		        // Perform any additional checks or operations if needed 
		        return true;
		    }
		    return false;
	    }
	 
//===============================================	Add User Method ===================================================================================
		public User userRegister(User u) {

			User user = userRepository.save(u);
			return user;
		}
	
//=================================================== Show All User Method =========================================================================
	
	public List<User> ShowAllUser(){
		
		List<User> findAll = (List<User>) userRepository.findAll();
		
		return findAll;
	}
}
