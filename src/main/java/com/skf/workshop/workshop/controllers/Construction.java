package com.skf.workshop.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;


@Controller
public class Construction {

    @GetMapping("/construction")
	public String search(HttpSession session) {
		return "construction/index.html";
	}
    
}
