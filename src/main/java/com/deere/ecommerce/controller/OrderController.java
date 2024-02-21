package com.deere.ecommerce.controller;

import com.deere.ecommerce.entity.Address;
import com.deere.ecommerce.entity.Order;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.OrderException;
import com.deere.ecommerce.exception.UserException;
import com.deere.ecommerce.service.OrderService;
import com.deere.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization")String jwt)throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Order order= orderService.createOrder(user,shippingAddress);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @GetMapping("/userOrderHistory")
    public ResponseEntity< List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                 String jwt) throws OrderException, UserException{
        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/findByOrderId")
    public ResponseEntity< Order> findOrderHandler(@RequestParam Long orderId, @RequestHeader("Authorization")
    String jwt) throws UserException, OrderException {

        User user=userService.findUserProfileByJwt(jwt);
        Order orders=orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }
}
