package com.oms.demo.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oms.demo.auth.dto.LoginRequest;
import com.oms.demo.auth.dto.LoginResponse;
import com.oms.demo.auth.dto.RegisterRequest;
import com.oms.demo.auth.dto.UserResponse;
import com.oms.demo.auth.entity.User;
import com.oms.demo.auth.repository.UserRepository;
import com.oms.demo.auth.security.JwtUtil;
import com.oms.demo.common.exception.InvalidCredentialsException;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder ,JwtUtil jwtUtil) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtUtil=jwtUtil;
	}
	
	public UserResponse register(RegisterRequest request) {
		
		User user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole()).build();
		
		User saved=userRepository.save(user);
		
		return UserResponse.fromEntity(saved);
	}
	
	public LoginResponse login(LoginRequest request) {
		
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
		
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new InvalidCredentialsException("Invalid username or password");
	    }
		
		
		String token = jwtUtil.generateToken(user.getUsername());
	    return new LoginResponse(token);
		
		
	}
	
	
}
