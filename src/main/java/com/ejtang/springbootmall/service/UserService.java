package com.ejtang.springbootmall.service;

import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;

public interface UserService {
	int register(UserRequest user);
	
	User getUserById(int userId);
}
