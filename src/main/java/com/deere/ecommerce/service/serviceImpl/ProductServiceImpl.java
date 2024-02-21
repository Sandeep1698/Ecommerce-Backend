package com.deere.ecommerce.service.serviceImpl;

import com.deere.ecommerce.dto.request.CreateProductRequest;
import com.deere.ecommerce.entity.Category;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.repository.CategoryRepository;
import com.deere.ecommerce.repository.ProductRepository;
import com.deere.ecommerce.service.ProductService;
import com.deere.ecommerce.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;

    @Override
    @Transactional
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLavelCategory());
        if(topLevel==null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLavelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory(),topLevel.getName());
        if(secondLevel==null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLavelCategory());
            secondLevelCategory.setLevel(1);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLavelCategory(),secondLevel.getName());
        if(thirdLevel==null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLavelCategory());
            thirdLevelCategory.setLevel(1);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        product.getOrderItems().clear();
        product.getCartItems().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Product req) throws ProductException {
        Product product = findProductById(req.getId());
        if (req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());
        }
        if (StringUtils.isEmpty(req.getImageUrl())){
            product.setImageUrl(req.getImageUrl());
        }
        if (StringUtils.isEmpty(req.getTitle())){
            product.setTitle(req.getTitle());
        }
        if (req.getPrice()!=0){
            product.setPrice(req.getPrice());
        }
        if (req.getDiscountedPrice()!=0){
            product.setDiscountedPrice(req.getDiscountedPrice());
        }
        if (req.getDiscountPercent()!=0){
            product.setDiscountPercent(req.getDiscountPercent());
        }
        if (StringUtils.isEmpty(req.getColor())){
            product.setColor(req.getColor());
        }
        if (StringUtils.isEmpty(req.getDescription())){
            product.setDescription(req.getDescription());
        }

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);
        if (opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id - "+id);
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes
            , Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock,
                                       Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);


        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());

        }

        if(stock!=null) {

            if(stock.equals("in_stock")) {
                products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            }
            else if (stock.equals("out_of_stock")) {
                products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }


        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
        return filteredProducts;
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
