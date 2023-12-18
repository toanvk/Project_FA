package com.fpt.service;

import com.fpt.dto.CartDto;
import com.fpt.model.Cart;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    Cart createCart(CartDto cartDto);

    Cart getCartById(Long cartId);
}