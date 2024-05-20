package com.ejtang.springbootmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejtang.springbootmall.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		User user = new User();
		// 後面要放DB的欄位
		user.setUserId(rs.getInt("user_id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setCreatedDate(rs.getTimestamp("created_date"));
		user.setLastModifiedDate(rs.getTimestamp("last_modified_date"));
		
		return user;
	}

}
