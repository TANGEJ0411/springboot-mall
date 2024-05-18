package com.ejtang.springbootmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ejtang.springbootmall.dao.UserDao;
import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;
import com.ejtang.springbootmall.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public int register(UserRequest user) {

		return userDao.createUser(user);
	}

	@Override
	public User getUserById(int userId) {
		
		return userDao.getUserById(userId);
	}
}
