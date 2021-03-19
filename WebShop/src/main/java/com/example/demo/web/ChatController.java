package com.example.demo.web;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/index"})
public class ChatController {

    @GetMapping
    public String getChatPage(Model model){
        return "index";
    }


}