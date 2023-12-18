package com.fpt.service;


import com.fpt.model.CartItem;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    CartItem getCartItemById(Long cartItemId);

    CartItem createCartItem(CartItem cartItem);
}