package com.ejtang.springbootmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ejtang.springbootmall.dao.OrderDao;
import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.dto.BuyItem;
import com.ejtang.springbootmall.dto.CreateOrderRequest;
import com.ejtang.springbootmall.model.Order;
import com.ejtang.springbootmall.model.OrderItem;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());
			int amount = buyItem.getQuantity() * product.getPrice();
			totalAmount = totalAmount + amount;

			// 轉換BuyItem to OrderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);

			orderItemList.add(orderItem);
		}

		// 創建訂單
		Integer orderId = orderDao.createOrder(userId, totalAmount);

		orderDao.createOrderItems(orderId, orderItemList);

		return orderId;
	}

	@Override
	public Order getOrderById(Integer orderId) {

		Order order = orderDao.getOrderById(orderId);

		order.setOrderItemList(orderDao.getOrderItemsByOrderId(orderId));

		return order;
	}

}
