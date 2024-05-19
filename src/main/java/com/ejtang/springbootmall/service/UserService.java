package com.ejtang.springbootmall.service;

import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;

public interface UserService {
	Integer register(UserRequest user);

	User login(UserRequest userRequest);

	User getUserById(int userId);

	User getUserByEmail(String email);
}
