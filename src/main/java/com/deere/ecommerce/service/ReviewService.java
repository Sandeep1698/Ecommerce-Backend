package com.deere.ecommerce.service;

import com.deere.ecommerce.dto.request.ReviewRequest;
import com.deere.ecommerce.entity.Review;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user)throws ProductException;
    public List<Review> getAllReview(Long productId);
}
