package com.ejtang.springbootmall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejtang.springbootmall.model.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

		Order order = new Order();

		order.setOrderId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setTotalAmount(rs.getInt("total_amount"));
		order.setCreatedDate(rs.getTimestamp("created_date"));
		order.setLastModifieDate(rs.getTimestamp("last_modified_date"));

		return order;
	}

}
