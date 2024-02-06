package com.FastKart.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.FastKart.Repository.AdminRepository;
import com.FastKart.entities.Admin;

public class adminDetailsImple implements UserDetailsService {
	
	@Autowired private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = adminRepository.findByUsername(username);

		if (admin == null) {
			throw new UsernameNotFoundException("could not load admin !!");

		}

		customAdminDetails auDetails = new customAdminDetails(admin);
		return auDetails;
	}

}
