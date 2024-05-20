package com.ejtang.springbootmall.dao.impl;

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

import com.ejtang.springbootmall.dao.OrderDao;
import com.ejtang.springbootmall.model.Order;
import com.ejtang.springbootmall.model.OrderItem;
import com.ejtang.springbootmall.rowmapper.OrderItemRowMapper;
import com.ejtang.springbootmall.rowmapper.OrderRowMapper;

@Component
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Integer createOrder(Integer userId, Integer totalAmount) {

		String sqlString = "INSERT INTO `order`(user_id, total_amount, created_date, "
				+ "last_modified_date) VALUES (:userId, :totalAmount, :createdDate, " + ":lastModifiedDate)";

		Map<String, Object> map = new HashMap<>();

		map.put("userId", userId);
		map.put("totalAmount", totalAmount);

		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sqlString, new MapSqlParameterSource(map), keyHolder);

		int orderId = keyHolder.getKey().intValue();

		return orderId;
	}

	@Override
	public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
		// batchUpdate
		String sqlString = "INSERT INTO order_item(order_id, product_id, quantity, amount) "
				+ "VALUES (:orderId, :productId, :quantity, :amount)";

		MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

		for (int i = 0; i < orderItemList.size(); i++) {
			OrderItem orderItem = orderItemList.get(i);

//			System.out.println(orderItem.getAmount());

			parameterSources[i] = new MapSqlParameterSource();
			parameterSources[i].addValue("orderId", orderId);
			parameterSources[i].addValue("productId", orderItem.getProductId());
			parameterSources[i].addValue("quantity", orderItem.getQuantity());
			parameterSources[i].addValue("amount", orderItem.getAmount());

		}

		namedParameterJdbcTemplate.batchUpdate(sqlString, parameterSources);

	}

	@Override
	public Order getOrderById(Integer orderId) {
		String sqlString = "SELECT order_id, user_id, total_amount, created_date, "
				+ "last_modified_date FROM `order` WHERE order_id=:orderId";

		Map<String, Object> map = new HashMap<>();

		map.put("orderId", orderId);

		List<Order> orderList = namedParameterJdbcTemplate.query(sqlString, map, new OrderRowMapper());

		if (orderList.size() > 0) {
			return orderList.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {

		String sqlString = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, "
				+ "oi.amount, p.product_name, p.image_url FROM order_item as oi LEFT JOIN "
				+ "product as p ON oi.product_id = p.product_id WHERE oi.order_id = :orderId";

		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);

		List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sqlString, map, new OrderItemRowMapper());

		return orderItemList;
	}

}
