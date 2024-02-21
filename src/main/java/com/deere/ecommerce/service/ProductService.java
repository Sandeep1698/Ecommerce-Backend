package com.deere.ecommerce.service;

import com.deere.ecommerce.dto.request.CreateProductRequest;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.exception.ProductException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);
    public String deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Product req)throws ProductException;
    public Product findProductById(Long id)throws ProductException;
    public Page<Product> getAllProduct(String category,List<String> colors,List<String> sizes,Integer minPrice
            ,Integer maxPrice,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
    public List<Product> findProductByCategory(String category);
    public List<Product> findAllProducts();
}
