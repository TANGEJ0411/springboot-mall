package com.ejtang.springbootmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.dto.ProductQueryParams;
import com.ejtang.springbootmall.dto.ProductRequest;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {

		return productDao.getProducts(productQueryParams);
	}

	@Override
	public Product getProductById(int productId) {

		return productDao.getProductById(productId);
	}

	@Override
	public int createProduct(ProductRequest productRequest) {
		return productDao.createProduct(productRequest);
	}

	@Override
	public void updateProduct(int productId, ProductRequest productRequest) {
		productDao.updateProduct(productId, productRequest);

	}

	@Override
	public void deleteProduct(int productId) {
		productDao.deleteProduct(productId);

	}

	@Override
	public int countProduct(ProductQueryParams productQueryParams) {
		
		return productDao.countProduct(productQueryParams);
	}

}
