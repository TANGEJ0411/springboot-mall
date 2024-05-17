package com.ejtang.springbootmall.service;

import com.ejtang.springbootmall.dto.ProductRequest;
import com.ejtang.springbootmall.model.Product;

public interface ProductService {
	Product getProductById(int productId);
	
	int createProduct(ProductRequest productRequest);
	
	void updateProduct(int productId, ProductRequest productRequest);
}
