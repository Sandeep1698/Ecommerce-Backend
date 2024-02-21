package com.deere.ecommerce.controller;

import com.deere.ecommerce.dto.request.CreateProductRequest;
import com.deere.ecommerce.dto.response.ApiResponse;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
        return new ResponseEntity<>(productService.createProduct(req), HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req) throws ProductException {
        return new ResponseEntity<>(productService.updateProduct(req),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long productId) throws ProductException{
        String msg=productService.deleteProduct(productId);
        ApiResponse res = new ApiResponse(msg,true);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @GetMapping("/findAllProducts")
    public ResponseEntity<List<Product>> findAllProducts(){
        return new ResponseEntity<List<Product>>(productService.findAllProducts(),HttpStatus.OK);
    }
}
