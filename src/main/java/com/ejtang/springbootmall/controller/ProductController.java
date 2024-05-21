package com.ejtang.springbootmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejtang.springbootmall.constant.ProductCategory;
import com.ejtang.springbootmall.dto.ProductQueryParams;
import com.ejtang.springbootmall.dto.ProductRequest;
import com.ejtang.springbootmall.model.Product;
import com.ejtang.springbootmall.service.ProductService;
import com.ejtang.springbootmall.util.Page;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Validated
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getProducts(
			// 查詢條件 filtering
			@RequestParam(required = false) ProductCategory category, 
			@RequestParam(required = false) String search,

			// 排序 sorting
			@RequestParam(defaultValue = "created_date") String orderBy,
			@RequestParam(defaultValue = "desc") String sort,

			// 分頁 pagination
			@RequestParam(defaultValue = "3") @Max(1000) @Min(0) int limit, // 要取得多少筆數據
			@RequestParam(defaultValue = "0") @Min(0) int offset // 要跳過多少筆數據
	) {

		ProductQueryParams productQueryParams = new ProductQueryParams();

		productQueryParams.setCategory(category);

		productQueryParams.setSearch(search);

		productQueryParams.setOrderBy(orderBy);

		productQueryParams.setSort(sort);

		productQueryParams.setLimit(limit);

		productQueryParams.setOffset(offset);

		// 取得productList
		List<Product> productList = productService.getProducts(productQueryParams);

		// 取得product的總筆數
		int total = productService.countProduct(productQueryParams);

		// 分頁的responseBody的值
		Page<Product> page = new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setResults(productList);

		return ResponseEntity.status(HttpStatus.OK).body(page);
	}

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
	public ResponseEntity<Product> updateProduct(
			@PathVariable int productId,
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

	@DeleteMapping("products/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable int productId) {

		productService.deleteProduct(productId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
