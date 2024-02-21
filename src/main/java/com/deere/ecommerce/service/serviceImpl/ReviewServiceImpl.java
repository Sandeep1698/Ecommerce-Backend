package com.deere.ecommerce.service.serviceImpl;

import com.deere.ecommerce.dto.request.ReviewRequest;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.entity.Review;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.repository.RatingRepository;
import com.deere.ecommerce.repository.ReviewRepository;
import com.deere.ecommerce.service.ProductService;
import com.deere.ecommerce.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductService productService;
    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(review.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
