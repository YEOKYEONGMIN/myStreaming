package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search/*")
public class SearchController {

    @GetMapping("/search")
    public void searchFrom(String keyword, Model model){

        System.out.println(keyword);
        model.addAttribute("keyword", keyword);
    }

}
