package com.ejtang.springbootmall.dao;

import java.util.List;

import com.ejtang.springbootmall.model.Order;
import com.ejtang.springbootmall.model.OrderItem;

public interface OrderDao {
	Integer createOrder(Integer userId, Integer totalAmount);

	void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

	Order getOrderById(Integer orderId);

	List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
