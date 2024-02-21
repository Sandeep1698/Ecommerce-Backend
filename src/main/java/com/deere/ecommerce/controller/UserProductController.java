package com.deere.ecommerce.controller;

import com.deere.ecommerce.dto.request.CreateProductRequest;
import com.deere.ecommerce.dto.response.ApiResponse;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class UserProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam String category, @RequestParam List<String> color,
            @RequestParam List<String> size,@RequestParam Integer minPrice,
            @RequestParam Integer maxPrice,@RequestParam Integer minDiscount,
            @RequestParam String sort,@RequestParam String stock,@RequestParam Integer pageNumber,
            @RequestParam Integer pageSize){

        Page<Product> res = productService.getAllProduct(category,color,size,minPrice,maxPrice,minDiscount,sort,stock,pageNumber,pageSize);
        return  new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getProductById")
    public ResponseEntity<Product> findProductByIdHandler(@RequestParam Long productId) throws ProductException {
        return new ResponseEntity<>(productService.findProductById(productId),HttpStatus.ACCEPTED);
    }
}
