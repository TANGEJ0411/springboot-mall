package com.ejtang.springbootmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable int productId) {
		Product product = productService.getProductById(productId);
		
		if (product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}else {
			// 由於沒有設置響應體，所以需要調用 build() 以創建一個僅包含狀態碼的 ResponseEntity 實例。
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
