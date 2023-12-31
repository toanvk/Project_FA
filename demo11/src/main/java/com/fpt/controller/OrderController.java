package com.fpt.controller;


import com.fpt.dto.ForgotRequest;
import com.fpt.dto.OrderDto;
import com.fpt.dto.OrderRequestDto;
import com.fpt.dto.TransactionDto;
import com.fpt.exceptions.ApiExceptionResponse;
import com.fpt.model.Order;
import com.fpt.model.StatusEnum;
import com.fpt.model.Transaction;
import com.fpt.service.LaptopService;
import com.fpt.service.OrderItemService;
import com.fpt.service.OrderService;
import com.fpt.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, LaptopService laptopService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/getOrders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        Order o = orderService.getOrderbyId(id);
        if (o != null) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(o));
        }
        return null;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        Order createdOrder = orderService.createOrder(orderDto);
        if (createdOrder != null) {
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(createdOrder));
        }
        return null;
    }

    @PostMapping("/{orderId}/add-laptop/{laptopId}/quantity")
    public ResponseEntity<?> addToOrder(@PathVariable Long orderId, @PathVariable Long laptopId, @RequestParam int quantity) {
        try {
            orderItemService.addLaptopToCart(orderId, laptopId, quantity);
            orderService.updateTotalPrice(orderId);
            return ResponseEntity.ok("Laptop has been added to the Order.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add laptop to the Order.");
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        try {
            StatusEnum statusEnum = StatusEnum.valueOf(status.toUpperCase());
            orderService.updateOrderStatus(orderId, statusEnum);
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status provided.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status.");
        }
    }
    @PostMapping("/confirmKey")
    public ResponseEntity<?> confirmKey(@Valid @RequestBody ForgotRequest forgotRequest) {
        boolean sent = orderService.sendConfirmPassToEmail(forgotRequest.getEmail());
        if (sent) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private OrderDto convertToDto(Order order){
        OrderDto o = new OrderDto();
        o.setId(order.getId());
        o.setFirstName(order.getFirstName());
        o.setLastName(order.getLastName());
        o.setStatus(order.getStatus());
        o.setEmail(order.getEmail());
        o.setPhoneNumber(order.getPhoneNumber());
        o.setLine(order.getLine());
        o.setProvince(order.getProvince());
        o.setTotalPrice(order.getTotalPrice());
        o.setUserId(order.getUser().getId());
        o.setCity(order.getCity());
        o.setCreatedAt(order.getCreatedAt());
        o.setUpdatedAt(order.getUpdatedAt());
        Set<Transaction> transactionSet = order.getTransactions();
        Set<TransactionDto> transactionDtoSet = new HashSet<>();

        if(transactionSet != null) {
            for (Transaction t: transactionSet) {
                TransactionDto tdto = convertToTransactionDto(t);
                transactionDtoSet.add(tdto);
            }
        }
        o.setTransactions(transactionDtoSet);
        return o;
    }

    private TransactionDto convertToTransactionDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setStatus(transaction.getStatus().toString());
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        dto.setOrderId(transaction.getOrder().getId());
        dto.setUserId(transaction.getUser().getId());
        return dto;
    }

    @PostMapping("/create-mul-order-item")
    public ResponseEntity<?> createOrderItems(@RequestBody OrderRequestDto orderRequestDto){
        try {
            boolean c = orderItemService.addMultiLaptopToCart(orderRequestDto);
            if(c){
                return ResponseEntity.ok("Laptop has been added to the Order.");
            }
        } catch (RuntimeException e) {
        }
        return ResponseEntity.badRequest().body("Failed to add laptop to the Order.");
    }

    @GetMapping("/getOrder/{userName}")
    public ResponseEntity<?> getOrderByUserName(@PathVariable String userName) {
        Set<OrderDto> dtoSet =  orderService.getOrderDtobyUserName(userName);
        if(dtoSet.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(dtoSet);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Fetch fail!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}