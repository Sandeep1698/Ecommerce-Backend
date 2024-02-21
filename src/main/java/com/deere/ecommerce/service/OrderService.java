package com.deere.ecommerce.service;

import com.deere.ecommerce.entity.Address;
import com.deere.ecommerce.entity.Order;
import com.deere.ecommerce.entity.User;
import com.deere.ecommerce.exception.OrderException;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address address);
    public Order findOrderById(Long orderId)throws OrderException;
    public List<Order> userOrderHistory(Long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order cancelledOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;




}
