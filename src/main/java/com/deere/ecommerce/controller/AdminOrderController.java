package com.deere.ecommerce.controller;

import com.deere.ecommerce.dto.response.ApiResponse;
import com.deere.ecommerce.entity.Order;
import com.deere.ecommerce.exception.OrderException;
import com.deere.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders=orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/confirmOrder")
    public ResponseEntity<Order> ConfirmedOrderHandler(@RequestParam Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException {
        Order order=orderService.confirmedOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/shipOrder")
    public ResponseEntity<Order> shippedOrderHandler(@RequestParam Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.shippedOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/deliverOrder")
    public ResponseEntity<Order> deliveredOrderHandler(@RequestParam Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.deliveredOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/cancelOrder")
    public ResponseEntity<Order> canceledOrderHandler(@RequestParam Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.cancelledOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@RequestParam Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        ApiResponse res=new ApiResponse("Order Deleted Successfully",true);
        System.out.println("delete method working....");
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

}
