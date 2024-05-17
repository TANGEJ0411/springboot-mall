package com.ejtang.springbootmall.service;

import java.util.List;

import com.ejtang.springbootmall.dto.ProductRequest;
import com.ejtang.springbootmall.model.Product;

public interface ProductService {

	List<Product> getProducts();

	Product getProductById(int productId);

	int createProduct(ProductRequest productRequest);

	void updateProduct(int productId, ProductRequest productRequest);

	void deleteProduct(int productId);
}
