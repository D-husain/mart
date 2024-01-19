package com.FastKart.APIController;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.FastKart.Dao.OrderDao;
import com.FastKart.Dao.userDao;
import com.FastKart.Dto.UserDTO;
import com.FastKart.entities.Orders;
import com.FastKart.entities.User;

@Controller
public class UserApiController {
	
	@Autowired private userDao udao;
	@Autowired private OrderDao odao;

	
	@GetMapping("api/user/data")
	public ResponseEntity<List<UserDTO>> getUserList() {
        try {
            List<User> userList = udao.ShowAllUser();
            List<UserDTO> userDtoList = convertToUserDtoList(userList);
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	private List<UserDTO> convertToUserDtoList(List<User> userList) {
	    return userList.stream()
	            .map(user -> new UserDTO(
	                    user.getId(),
	                    user.getUsername(),
	                    user.getEmail(), 
	                    user.getPassword(), 
	                    user.getRole(),
	                    false, 
	                    0, null, null, null, null, null, null, 0, user.getCreated_at(), 
	                    user.getUpdated_at()
	            ))
	            .collect(Collectors.toList());
	}

	
	/*
	 * @GetMapping("api/user/orders/{userId}") public ResponseEntity<List<Orders>>
	 * getUserOrders(@RequestBody User user) { List<Orders> userOrders =
	 * odao.viewOrders(user.getId());
	 * 
	 * if (userOrders.isEmpty()) { return new
	 * ResponseEntity<>(HttpStatus.NO_CONTENT); } else { return new
	 * ResponseEntity<>(userOrders, HttpStatus.OK); } }
	 */
	
	@GetMapping("api/user/orders/{userId}")
	public ResponseEntity<List<Orders>> getUserOrders(@PathVariable int userId) {
	    if (userId == 0) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    List<Orders> userOrders = odao.viewOrders(userId);

	    if (userOrders.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } else {
	        return new ResponseEntity<>(userOrders, HttpStatus.OK);
	    }
	}

}
