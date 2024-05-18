package com.ejtang.springbootmall.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ejtang.springbootmall.dao.UserDao;
import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;
import com.ejtang.springbootmall.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public Integer register(UserRequest userRequest) {
		// 檢查 email 是否有被註冊
		User user = userDao.getUserByEmail(userRequest.getEmail());

		if (user != null) {
			LOG.warn("該email {} 已被註冊", userRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		// 實際創建user資料
		return userDao.createUser(userRequest);

	}

	@Override
	public User getUserById(int userId) {

		return userDao.getUserById(userId);
	}

	@Override
	public User getUserByEmail(String email) {

		return userDao.getUserByEmail(email);
	}
}
