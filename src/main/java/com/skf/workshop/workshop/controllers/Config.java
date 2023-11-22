package com.skf.workshop.workshop.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;


@Controller
public class Config {

    @GetMapping("/config")
    public String config(HttpSession session, HttpServletRequest request, Model model, @CookieValue(value="userrole") String userrole) {
      if(null == request.getSession().getAttribute("username")) return "redirect:/login";
      if(!userrole.equals("YWRtaW4=")) return "redirect:/login";

      model.addAttribute("content","");
      model.addAttribute("msg","");
      return "config/index.html";
      }

    @GetMapping("/config/{input}")
    public String input(@PathVariable String input, HttpSession session, HttpServletRequest request, Model model, @CookieValue(value="userrole") String userrole) {
        if(null == request.getSession().getAttribute("username")) return "redirect:/login";
        if(!userrole.equals("YWRtaW4=")) return "redirect:/login";

        byte[] decodedInputBytes = Base64.getDecoder().decode(input);
        String decodedString = new String(decodedInputBytes);
        Yaml yaml = new Yaml();
        Object obj = yaml.load(decodedString);     
        model.addAttribute("content",obj.toString());
        model.addAttribute("msg","The server configuration has been restored to the original state: ");
        return "config/index.html";
      }

   
}
