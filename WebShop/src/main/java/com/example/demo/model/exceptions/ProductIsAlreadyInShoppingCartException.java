package com.example.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ProductIsAlreadyInShoppingCartException extends RuntimeException {
    public ProductIsAlreadyInShoppingCartException(String productName) {
        super(String.format("Product %s is alraedy in active shopping cart", productName));
    }
}
