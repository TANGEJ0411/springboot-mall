package com.ejtang.springbootmall.dao;

import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;

public interface UserDao {

	Integer createUser(UserRequest userRequest);

	User getUserByEmail(String email);

	User getUserById(int userId);
}
