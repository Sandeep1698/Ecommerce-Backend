package com.deere.ecommerce.service;

import com.deere.ecommerce.entity.Cart;
import com.deere.ecommerce.entity.CartItem;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.exception.CartItemException;
import com.deere.ecommerce.exception.UserException;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId,Long id,int quantity) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;
    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
