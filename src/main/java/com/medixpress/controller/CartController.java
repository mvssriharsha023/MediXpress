package com.medixpress.controller;

import com.medixpress.dto.CartItemDTO;
import com.medixpress.exception.AuthenticationException;
import com.medixpress.model.CartItem;
import com.medixpress.security.JwtUtil;
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
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public CartItem addToCart(@RequestHeader String token, @RequestBody CartItemDTO dto) {
        if (token == null || token.isBlank()) {
            throw new AuthenticationException("Please login to add medicines into cart");
        }
        String userId = jwtUtil.extractId(token);
        dto.setUserId(userId);
        return cartService.addToCart(dto);
    }

    @GetMapping("/user")
    public List<CartItemDTO> getUserCart(@RequestHeader String token) {
        if (token == null || token.isBlank()) {
            throw new AuthenticationException("Please login to view cart items");
        }
        String userId = jwtUtil.extractId(token);
        return cartService.getCartItemsByUser(userId);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeItem(@PathVariable String cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Removed " + cartItemId + " from cart successfully.");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestHeader String token) {
        if (token == null || token.isBlank()) {
            throw new AuthenticationException("Please login to clear the cart");
        }
        String userId = jwtUtil.extractId(token);
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
