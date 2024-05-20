package com.ejtang.springbootmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ejtang.springbootmall.dao.OrderDao;
import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.dao.UserDao;
import com.ejtang.springbootmall.dto.BuyItem;
import com.ejtang.springbootmall.dto.CreateOrderRequest;
import com.ejtang.springbootmall.model.Order;
import com.ejtang.springbootmall.model.OrderItem;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.model.User;
import com.ejtang.springbootmall.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
		// 檢查 user是否存在
		User user = userDao.getUserById(userId);

		if (user == null) {
			LOG.warn("用戶不存在");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "用戶不存在");
		}

		int totalAmount = 0;
		List<OrderItem> orderItemList = new ArrayList<>();

		for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product = productDao.getProductById(buyItem.getProductId());

			// 檢查product是否存在，和庫存是否足夠
			if (product == null) {
				LOG.warn("商品不存在");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "商品不存在");
			} else if (buyItem.getQuantity() > product.getStock()) {
				LOG.warn("購買商品數量超過庫存");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "購買商品數量超過庫存");
			}

			// 扣除商品庫存
			productDao.updateProductStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

			// 計算價錢
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
