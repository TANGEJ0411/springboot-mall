package com.ejtang.springbootmall.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ejtang.springbootmall.model.Product;

public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setProductId(rs.getInt("product_id"));
		product.setProductName(rs.getString("product_name"));
		product.setCategory(rs.getString("category"));
		product.setImageUrl(rs.getString("image_url"));
		product.setPrice(rs.getInt("price"));
		product.setStock(rs.getInt("price"));
		product.setDescription(rs.getString("description"));
		product.setCreatedDate(new Date(rs.getTimestamp("created_date").getTime()));
		product.setLastModifiedDate(new Date(rs.getTimestamp("last_modified_date").getTime()));
		
		return product;
	}
	
}
