package com.deere.ecommerce.controller;

import com.deere.ecommerce.dto.response.ApiResponse;
import com.deere.ecommerce.entity.CartItem;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.CartItemException;
import com.deere.ecommerce.exception.UserException;
import com.deere.ecommerce.service.CartItemService;
import com.deere.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<ApiResponse> deleteCartItem(@RequestParam Long cartItemId, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException {
        User user= userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res = new ApiResponse("Item removed from the cart.",true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateCartItem")
    public ResponseEntity<CartItem> updateCartItem(@RequestParam Long cartItemId,@RequestParam int quantity, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedCart = cartItemService.updateCartItem(user.getId(), cartItemId, quantity);
        return new ResponseEntity<CartItem>(updatedCart, HttpStatus.ACCEPTED);
    }
}
