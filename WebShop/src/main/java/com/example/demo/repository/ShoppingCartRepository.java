package com.example.demo.repository;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserAndStatus(User user, ShoppingCartStatus status);
    Optional<ShoppingCart> findByUserUsernameAndStatus(String username, ShoppingCartStatus status);
    boolean existsByUserUsernameAndStatus(String username, ShoppingCartStatus status);
    List<ShoppingCart> findAllByUserUsername(String username);
}
