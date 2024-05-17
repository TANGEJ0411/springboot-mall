package com.ejtang.springbootmall.dao.imlp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ejtang.springbootmall.dao.ProductDao;
import com.ejtang.springbootmall.dto.ProductRequest;
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

	@Override
	public int createProduct(ProductRequest productRequest) {
		String sqlString = "INSERT INTO product (product_name, category, "
				+ "image_url, price, stock, description, created_date, "
				+ "last_modified_date) VALUES (:productName, :category, "
				+ ":imageUrl, :price, :stock, :description, :createdDate, "
				+ ":lastModifiedDate);";

		Map<String, Object> map = new HashMap<>();
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().name());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());

		Date now = new Date();
		map.put("createdDate", now);
		map.put("lastModifiedDate", now);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		namedParameterJdbcTemplate.update(sqlString, new MapSqlParameterSource(map), keyHolder);
		
		int productId = keyHolder.getKey().intValue();
		
		return productId;
	}

	@Override
	public void updateProduct(int productId, ProductRequest productRequest) {
		
		String sqlString ="UPDATE product SET product_name = :productName, "
				+ "category=:category, image_url=:imageUrl, price=:price, "
				+ "stock=:stock, description=:description, "
				+ "last_modified_date=:lastModifiedDate WHERE product_id=:productId";
		
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		map.put("productName", productRequest.getProductName());
		map.put("category", productRequest.getCategory().toString());
		map.put("imageUrl", productRequest.getImageUrl());
		map.put("price", productRequest.getPrice());
		map.put("stock", productRequest.getStock());
		map.put("description", productRequest.getDescription());

		Date now = new Date();
		map.put("lastModifiedDate", now);

		namedParameterJdbcTemplate.update(sqlString, map);
	}

	@Override
	public void deleteProduct(int productId) {
		String sqString = "DELETE FROM product WHERE product_id=:productId";
		
		Map<String, Object> map=  new HashMap<>();
		
		map.put("productId", productId);
		
		namedParameterJdbcTemplate.update(sqString, map);
		
	}
}