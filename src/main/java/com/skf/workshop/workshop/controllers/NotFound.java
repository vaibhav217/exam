package com.skf.workshop.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.LabelUI;

@Controller
public class NotFound {

    @GetMapping("/error/{code}")
    public String error404(@PathVariable String code,HttpSession session, Model model){
        return "error/"+code;
    }
    
}
