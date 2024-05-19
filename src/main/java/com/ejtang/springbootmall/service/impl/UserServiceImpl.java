package com.ejtang.springbootmall.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.ejtang.springbootmall.dao.UserDao;
import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;
import com.ejtang.springbootmall.service.UserService;

// Dao層只可以去和資料庫溝通，但在service可以寫複雜的判斷邏輯
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

		// hash密碼
		String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());

		userRequest.setPassword(hashedPassword);

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

	@Override
	public User login(UserRequest userRequest) {

		User user = userDao.getUserByEmail(userRequest.getEmail());

		// hash密碼
		String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());

		userRequest.setPassword(hashedPassword);

		if (user == null) {
			LOG.warn("該email {} 未被註冊", userRequest.getEmail());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (user.getPassword().equals(userRequest.getPassword())) {
			return user;
		} else {
			LOG.warn("輸入密碼不正確");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}
}
