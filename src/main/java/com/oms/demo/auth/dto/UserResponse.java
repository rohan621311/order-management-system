package com.oms.demo.auth.dto;

import com.oms.demo.auth.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private Long id;
	
	private String username;
	
	private String email;
	
	private User.Role role;
	
	public static UserResponse fromEntity(User user) {
		UserResponse userResponse= new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setUsername(user.getUsername());
		userResponse.setEmail(user.getEmail());
		userResponse.setRole(user.getRole());
		
		return userResponse;
		
	}
	
}
