package com.deere.ecommerce.controller;

import com.deere.ecommerce.dto.response.ApiResponse;
import com.deere.ecommerce.dto.response.PaymentLinkResponse;
import com.deere.ecommerce.entity.Order;
import com.deere.ecommerce.exception.OrderException;
import com.deere.ecommerce.repository.OrderRepository;
import com.deere.ecommerce.service.OrderService;
import com.deere.ecommerce.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.deere.ecommerce.user.OrderStatus.PLACED;
import static com.deere.ecommerce.user.PaymentStatus.COMPLETED;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository  orderRepository;

    @PostMapping("/payments")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestParam Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);
        try{
            RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",order.getTotalDiscountedPrice()*100);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer = new JSONObject();
            customer.put("name",order.getUser().getFirstName()+" "+order.getUser().getLastName());
            customer.put("email",order.getUser().getEmail());
            paymentLinkRequest.put("customer",customer);

            JSONObject notify = new JSONObject();
            notify.put("sms",true);
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);
            paymentLinkRequest.put("reminder_enable",true);
            paymentLinkRequest.put("callback_url","http://localhost:3000/payment/"+orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse response = new PaymentLinkResponse(paymentLinkUrl,paymentLinkId);

            return new ResponseEntity<PaymentLinkResponse>(response, HttpStatus.CREATED);
        }catch(Exception e){
            throw  new RazorpayException(e.getMessage());
        }
    }

    @GetMapping("/payments/redirect")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,@RequestParam(name = "order_id")Long orderId) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecret);
        try{
            Payment payment = razorpay.payments.fetch(paymentId);
            if(payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus(COMPLETED);
                order.setOrderStatus(PLACED);
                orderRepository.save(order);
            }
            ApiResponse response = new ApiResponse("Your order got placed",true);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }catch (Exception e){
            throw  new RazorpayException(e.getMessage());
        }
    }
}
