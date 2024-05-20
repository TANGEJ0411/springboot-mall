package com.ejtang.springbootmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejtang.springbootmall.dto.CreateOrderRequest;
import com.ejtang.springbootmall.model.Order;
import com.ejtang.springbootmall.service.OrderService;

import jakarta.validation.Valid;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<?> createOrder(@PathVariable Integer userId,
			@RequestBody @Valid CreateOrderRequest createOrderRequest) {

		Integer orderId = orderService.createOrder(userId, createOrderRequest);

		Order order = orderService.getOrderById(orderId);

		return ResponseEntity.status(HttpStatus.CREATED).body(order);

	}

}