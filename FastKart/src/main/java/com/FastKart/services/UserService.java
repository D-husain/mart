package com.FastKart.services;

import com.FastKart.Dto.UserDTO;
import com.FastKart.entities.User;

public interface UserService {
	
	void saveUser(UserDTO userDto);

	User findUserByEmail(String email);
}