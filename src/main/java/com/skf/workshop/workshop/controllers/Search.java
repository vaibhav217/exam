package com.skf.workshop.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;


@Controller
public class Search {
    @PostMapping("/search")
	public String search(@RequestParam(name="search", required=false) String search, Model model,HttpSession session) {
		search = search.replaceAll("[/]", " ");
		model.addAttribute("search", search);
		return "search/index.html";
	}
}
