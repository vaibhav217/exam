package com.skf.workshop.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;



@Controller
public class Offers {

    @CrossOrigin(origins = "*")
    @RequestMapping(
      value = "/offers",
      method = {
          RequestMethod.GET,
          RequestMethod.POST
      }
  )
  public String offers(HttpServletRequest request, HttpSession session){
    if(null !=request.getSession().getAttribute("loggedin")){
        return "offers/index.html";
    }else{
        return "dashboard/index.html";
    }
  }

}
