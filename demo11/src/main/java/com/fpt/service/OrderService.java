package com.fpt.service;

import com.fpt.dto.OrderDto;
import com.fpt.model.Order;
import com.fpt.model.StatusEnum;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface OrderService {
    Order createOrder(OrderDto orderDto);

    Order getOrderbyId(Long id);

    void updateOrderStatus(Long orderId, StatusEnum newStatus);
    void updateTotalPrice(Long id);
    boolean sendConfirmPassToEmail(String email);

    Set<OrderDto> getOrderDtobyUserName(String username);
}