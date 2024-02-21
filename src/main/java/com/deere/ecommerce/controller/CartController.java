package com.deere.ecommerce.controller;

import com.deere.ecommerce.dto.request.AddItemRequest;
import com.deere.ecommerce.dto.response.ApiResponse;
import com.deere.ecommerce.entity.Cart;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.ProductException;
import com.deere.ecommerce.exception.UserException;
import com.deere.ecommerce.service.CartService;
import com.deere.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/findUserCart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/addItemToCart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization")String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);
        ApiResponse response = new ApiResponse("Item added to cart successfully",true);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
    }
}
