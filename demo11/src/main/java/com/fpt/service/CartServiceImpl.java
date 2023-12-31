package com.fpt.service;

import com.fpt.repository.CartRepository;
import com.fpt.repository.UserRepository;
import com.fpt.dto.CartDto;
import com.fpt.model.Cart;
import com.fpt.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart createCart(CartDto cartDto) {
        Cart cart = new Cart();
        Long userId = cartDto.getUserId();
        User user = userRepository.findById(userId).orElse(null);
        cart.setUser(user);
        cart.setStatus(cartDto.getStatus());
        cart.setFirstName(cartDto.getFirstName());
        cart.setLastName(cartDto.getLastName());
        cart.setPhoneNumber(cartDto.getPhoneNumber());
        cart.setEmail(cartDto.getEmail());
        cart.setLine(cartDto.getLine());
        cart.setCity(cartDto.getCity());
        cart.setProvince(cartDto.getProvince());
        cart.setContent(cartDto.getContent());
        cartRepository.save(cart);
        return cart;

    }
    @Override
    public Cart getCartById(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            return optionalCart.get();
        } else {
            throw new RuntimeException("Cart not found with id: " + cartId);
        }
    }

}