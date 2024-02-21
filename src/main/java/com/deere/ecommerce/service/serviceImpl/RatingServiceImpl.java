package com.deere.ecommerce.service.serviceImpl;

import com.deere.ecommerce.dto.request.RatingRequest;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.entity.Rating;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.repository.RatingRepository;
import com.deere.ecommerce.service.ProductService;
import com.deere.ecommerce.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;
    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
