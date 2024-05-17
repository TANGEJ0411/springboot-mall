package com.ejtang.springbootmall.dao.imlp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.rowmapper.ProductRowMapper;

@Component
public class ProductDaoImlp implements ProductDao {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Product getProductById(int productId) {

		String sqlString = "SELECT product_id, product_name, category, image_url, "
				+ "price, stock, description, created_date, "
				+ "last_modified_date FROM product WHERE product_id = :productId";

		Map<String, Object> map = new HashMap<>();

		map.put("productId", productId);

		List<Product> productList = namedParameterJdbcTemplate.query(sqlString, map, new ProductRowMapper());

		if (productList.size() > 0) {
			return productList.get(0);
		} else {
			return null;
		}

	}
}