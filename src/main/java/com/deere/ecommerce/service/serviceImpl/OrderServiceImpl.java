package com.deere.ecommerce.service.serviceImpl;

import com.deere.ecommerce.entity.*;
import com.deere.ecommerce.exception.OrderException;
import com.deere.ecommerce.repository.AddressRepository;
import com.deere.ecommerce.repository.OrderItemRepository;
import com.deere.ecommerce.repository.OrderRepository;
import com.deere.ecommerce.repository.UserRepository;
import com.deere.ecommerce.service.CartService;
import com.deere.ecommerce.service.OrderItemService;
import com.deere.ecommerce.service.OrderService;
import com.deere.ecommerce.service.ProductService;
import com.deere.ecommerce.user.OrderStatus;
import com.deere.ecommerce.user.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public Order createOrder(User user, Address address) {
        address.setUser(user);
        Address saveAddress = addressRepository.save(address);
        user.getAddresses().add(saveAddress);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        cart.getCartItems().forEach( item-> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());
            orderItems.add(orderItemRepository.save(orderItem));
        });

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder=orderRepository.save(createdOrder);
        orderItems.forEach(item -> {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        });
        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new OrderException("order not exist with id "+orderId);
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepository.getUsersOrders(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if(order!=null){
            order.setOrderStatus(OrderStatus.PLACED);
            order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
            return orderRepository.save(order);
        }
        throw new OrderException("Order Not Found !!");
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if(order!=null){
            order.setOrderStatus(OrderStatus.CONFIRMED);
            return orderRepository.save(order);
        }
        throw new OrderException("Order Not Found !!");
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if(order!=null){
            order.setOrderStatus(OrderStatus.SHIPPED);
            return orderRepository.save(order);
        }
        throw new OrderException("Order Not Found !!");
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if(order!=null){
            order.setOrderStatus(OrderStatus.DELIVERED);
            return orderRepository.save(order);
        }
        throw new OrderException("Order Not Found !!");
    }

    @Override
    public Order cancelledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        if(order!=null){
            order.setOrderStatus(OrderStatus.CANCELLED);
            return orderRepository.save(order);
        }
        throw new OrderException("Order Not Found !!");
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if(opt.isPresent()){
            Order order= opt.get();
            orderRepository.deleteById(order.getId());
        }
        throw new OrderException("Order Not Found !!");

    }
}
