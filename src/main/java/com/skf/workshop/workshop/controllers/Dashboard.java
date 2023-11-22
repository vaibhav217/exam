package com.skf.workshop.workshop.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;

import com.skf.workshop.workshop.dao.DashboardDAO;
import com.skf.workshop.workshop.model.Page;



@Controller
public class Dashboard {
    @Autowired
    private DashboardDAO dashboardDAO;

    @GetMapping("/dashboard/{id}")
    public String home(@PathVariable int id, HttpSession session, Model model) {
        if(id==0) id=1;
        List<Page> pages = dashboardDAO.getPage(id);
        model.addAttribute("page", pages.get(0));
        return "dashboard/index.html";
      }

    @GetMapping("/")
    public String home(HttpSession session) {
        return "redirect:/dashboard/1";
      }
    
}
