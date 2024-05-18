package com.ejtang.springbootmall.dao;

import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;

public interface UserDao {

	int createUser(UserRequest user);
	
	User getUserById(int userId);
}
