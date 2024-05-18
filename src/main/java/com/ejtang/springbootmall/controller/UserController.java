package com.ejtang.springbootmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;
import com.ejtang.springbootmall.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("users/register")
	public ResponseEntity<User> register(@RequestBody @Valid UserRequest userRequest) {

		Integer userId = userService.register(userRequest);

		User user = userService.getUserById(userId);

		return ResponseEntity.status(HttpStatus.CREATED).body(user);

	}

}
