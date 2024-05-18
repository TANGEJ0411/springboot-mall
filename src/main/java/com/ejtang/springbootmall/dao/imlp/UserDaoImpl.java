package com.ejtang.springbootmall.dao.imlp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ejtang.springbootmall.dao.UserDao;
import com.ejtang.springbootmall.dto.UserRequest;
import com.ejtang.springbootmall.model.User;
import com.ejtang.springbootmall.rowmapper.UserRowMapper;

@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int createUser(UserRequest user) {
		String sqlString = "INSERT INTO user (email, password, created_date, "
				+ "last_modified_date) VALUES (:email, :password, :createdDate, :lastModifiedDate)";

		Map<String, Object> map = new HashMap<>();

		map.put("email", user.getEmail());

		map.put("password", user.getPassword());

		Date now = new Date();

		map.put("createdDate", now);

		map.put("lastModifiedDate", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sqlString, new MapSqlParameterSource(map), keyHolder);

		int productId = keyHolder.getKey().intValue();

		return productId;
	}

	@Override
	public User getUserById(int userId) {

		String sqlString = "SELECT user_id, email, password, created_date, last_modified_date FROM user "
				+ "WHERE user_id=:userId";

		Map<String, Object> map = new HashMap<>();

		map.put("userId", userId);

		List<User> userList = namedParameterJdbcTemplate.query(sqlString, map, new UserRowMapper());

		if (userList.size() > 0) {
			return userList.get(0);
		} else {
			return null;
		}

	}

}
