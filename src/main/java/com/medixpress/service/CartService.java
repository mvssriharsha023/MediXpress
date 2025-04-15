package com.medixpress.service;

import com.medixpress.dto.CartItemDTO;
import com.medixpress.model.CartItem;

import java.util.List;

public interface CartService {

    CartItem addToCart(CartItemDTO cartItemDTO);
    List<CartItemDTO> getCartItemsByUser(String userId);
    void removeCartItem(String cartItemId);
    void clearCart(String userId);
    CartItemDTO updateQuantity(String cartItemId, Integer newQuantity);
}
