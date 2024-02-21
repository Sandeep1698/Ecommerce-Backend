package com.deere.ecommerce.service;

import com.deere.ecommerce.dto.request.AddItemRequest;
import com.deere.ecommerce.entity.Cart;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;

public interface CartService {
    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
