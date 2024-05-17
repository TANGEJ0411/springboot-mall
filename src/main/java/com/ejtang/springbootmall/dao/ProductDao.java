
package com.ejtang.springbootmall.dao;

import java.util.List;

import com.ejtang.springbootmall.constant.ProductCategory;
import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.dto.ProductRequest;
import com.ejtang.springbootmall.model.Product;

public interface ProductDao {
	List<Product> getProducts(ProductCategory category, String search);

	Product getProductById(int productId);

	int createProduct(ProductRequest productRequest);

	void updateProduct(int productId, ProductRequest productRequest);

	void deleteProduct(int productId);
}
