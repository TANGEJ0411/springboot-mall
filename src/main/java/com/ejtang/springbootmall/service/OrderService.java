package com.ejtang.springbootmall.service;

import java.util.List;

import com.ejtang.springbootmall.dto.CreateOrderRequest;
import com.ejtang.springbootmall.dto.OrderQueryParams;
import com.ejtang.springbootmall.model.Order;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

	Order getOrderById(Integer orderId);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	Integer countOrder(OrderQueryParams orderQueryParams);

}
