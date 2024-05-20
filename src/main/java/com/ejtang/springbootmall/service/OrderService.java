package com.ejtang.springbootmall.service;

import com.ejtang.springbootmall.dto.CreateOrderRequest;
import com.ejtang.springbootmall.model.Order;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

	Order getOrderById(Integer orderId);

}
