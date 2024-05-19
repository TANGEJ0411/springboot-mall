package com.ejtang.springbootmall.service;

import com.ejtang.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

	Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
