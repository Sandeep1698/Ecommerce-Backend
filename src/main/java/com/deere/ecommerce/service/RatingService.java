package com.deere.ecommerce.service;

import com.deere.ecommerce.dto.request.RatingRequest;
import com.deere.ecommerce.entity.Rating;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);
}
