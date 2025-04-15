package com.medixpress.controller;

import com.medixpress.dto.CartItemDTO;
import com.medixpress.model.CartItem;
import com.medixpress.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody CartItemDTO dto) {
        return cartService.addToCart(dto);
    }

    @GetMapping("/user/{userId}")
    public List<CartItemDTO> getUserCart(@PathVariable String userId) {
        return cartService.getCartItemsByUser(userId);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeItem(@PathVariable String cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Removed " + cartItemId + " from cart successfully.");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Clear cart successfully.");
    }

    @PutMapping("/update/{cartItemId}")
    public CartItemDTO updateQuantity(
            @PathVariable String cartItemId,
            @RequestBody Integer quantity) {
        return cartService.updateQuantity(cartItemId, quantity);
    }
}
