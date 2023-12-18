package com.fpt.service;

import com.fpt.dto.OrderRequestDto;
import com.fpt.model.OrderItem;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {
    OrderItem createOderItem(OrderItem orderItem);

    void addLaptopToCart(Long orderId, Long laptopId, int quantity);

    boolean addMultiLaptopToCart(OrderRequestDto orderRequestDto);
}