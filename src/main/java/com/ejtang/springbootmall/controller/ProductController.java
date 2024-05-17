package com.ejtang.springbootmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ejtang.springbootmall.dto.ProductRequest;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.service.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable int productId) {
		Product product = productService.getProductById(productId);

		if (product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		} else {
			// 由於沒有設置響應體，所以需要調用 build() 以創建一個僅包含狀態碼的 ResponseEntity 實例。
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest) {

		int productId = productService.createProduct(productRequest);

		Product product = productService.getProductById(productId);

		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}

	@PutMapping("products/{productId}")
	public ResponseEntity<Product> putMethodName(@PathVariable int productId,
			@RequestBody @Valid ProductRequest productRequest) {
		
		// 檢查product 是否存在
		Product product = productService.getProductById(productId);
		
		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		// 修改商品的數據
		productService.updateProduct(productId, productRequest);
		
		Product updatedProduct = productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

	}

}
