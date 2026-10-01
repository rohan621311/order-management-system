package com.oms.demo.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oms.demo.auth.dto.LoginRequest;
import com.oms.demo.auth.dto.LoginResponse;
import com.oms.demo.auth.dto.RegisterRequest;
import com.oms.demo.auth.dto.UserResponse;
import com.oms.demo.auth.entity.User;
import com.oms.demo.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rjg/auth")
public class AuthController {

	private final AuthService authservice;
	
	public AuthController(AuthService authservice) {
		this.authservice=authservice;
	}
	 
	@PostMapping("/register")
	public UserResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
		return authservice.register(registerRequest);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		return authservice.login(request);
	}
	
	
	
}
