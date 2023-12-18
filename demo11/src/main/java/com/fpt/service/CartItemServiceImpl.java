package com.fpt.service;

import com.fpt.repository.CartItemRepository;
import com.fpt.repository.LaptopRepository;
import com.fpt.model.CartItem;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;

    private final LaptopRepository laptopRepository;

    private final CartService cartService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, LaptopRepository laptopRepository, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.laptopRepository = laptopRepository;
        this.cartService = cartService;
    }

    @Override
    public CartItem getCartItemById(Long cartItemId) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent()) {
            return optionalCartItem.get();
        } else {
            throw new RuntimeException("CartItem not found with id: " + cartItemId);
        }
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }
}