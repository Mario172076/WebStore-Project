package com.example.demo.web;
import com.example.demo.model.Category;

import com.example.demo.model.exceptions.ProductIsAlreadyInShoppingCartException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = {"/contact"})
public class ContactController {

    //contact form
    @GetMapping
    public String getContactPage(Model model){
        return "contact";
    }







}