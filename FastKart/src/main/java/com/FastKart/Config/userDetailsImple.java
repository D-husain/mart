package com.FastKart.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.FastKart.Repository.UserRepository;
import com.FastKart.entities.User;

public class userDetailsImple implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.getUserByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("could not load user !!");

		}

		customUserDetails cuDetails = new customUserDetails(user);
		return cuDetails;
	}

}
