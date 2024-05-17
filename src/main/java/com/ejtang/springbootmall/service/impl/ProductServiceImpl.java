package com.ejtang.springbootmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public Product getProductById(int productId) {

		return productDao.getProductById(productId);
	}
}