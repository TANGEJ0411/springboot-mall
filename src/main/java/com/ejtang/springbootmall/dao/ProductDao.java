
package com.ejtang.springbootmall.dao;

import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.model.Product;

public interface ProductDao {
	Product getProductById(int productId);
}