package com.deere.ecommerce.service.serviceImpl;

import com.deere.ecommerce.entity.OrderItem;
import com.deere.ecommerce.repository.OrderItemRepository;
import com.deere.ecommerce.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
