package com.example.demo.service;

import com.example.demo.dto.ChargeRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    List<Product> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addProductToShoppingCart(String username, Long productId);
    ShoppingCart removeProductFromShoppingCart(String userId, Long productId);
    ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart createNewShoppingCart(String userId);
    List<ShoppingCart> findAllByUsername(String userId);

}
