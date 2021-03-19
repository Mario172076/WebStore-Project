package com.example.demo.service;

import com.example.demo.model.User;

public interface AuthService {
    User login(String username, String password);
    User getCurrentUser();
    String getCurrentUserId();
}