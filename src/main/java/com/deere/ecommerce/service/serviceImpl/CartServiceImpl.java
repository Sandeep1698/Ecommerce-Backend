package com.deere.ecommerce.service.serviceImpl;

import com.deere.ecommerce.dto.request.AddItemRequest;
import com.deere.ecommerce.entity.Cart;
import com.deere.ecommerce.entity.CartItem;
import com.deere.ecommerce.entity.Product;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.repository.CartRepository;
import com.deere.ecommerce.service.CartItemService;
import com.deere.ecommerce.service.CartService;
import com.deere.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart,product,req.getSize(),userId);
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            cartItem.setPrice(req.getQuantity()* product.getDiscountedPrice());
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }

        return "Item added to cart !!";
    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId);

        AtomicInteger totalPrice = new AtomicInteger();
        AtomicInteger totalDiscountedPrice = new AtomicInteger();
        AtomicInteger totalItem = new AtomicInteger();
        cart.getCartItems().forEach(cartItem -> {
            totalPrice.set(totalPrice.get() + cartItem.getPrice());
            totalDiscountedPrice.set(totalDiscountedPrice.get() + cartItem.getDiscountedPrice());
            totalItem.set(totalItem.get() + cartItem.getQuantity());

        });
        cart.setTotalDiscountedPrice(totalDiscountedPrice.get());
        cart.setTotalPrice(totalPrice.get());
        cart.setTotalItem(totalItem.get());
        cart.setDiscount(totalPrice.get() - totalDiscountedPrice.get());

        return cartRepository.save(cart);
    }
}
