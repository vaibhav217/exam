package com.skf.workshop.workshop.controllers;

import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import org.springframework.web.bind.annotation.PathVariable;
import com.skf.workshop.workshop.dao.LoginDAO;
import com.skf.workshop.workshop.model.User;


@Controller
public class Login {

    @Autowired
    private LoginDAO loginDAO;

    @GetMapping("/login")
    public String login(@RequestParam(name="username", required=false) String username, @RequestParam(name="password", required=false) String password, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
        if(null == username){
            return "login/index.html";
        }
        List<User> users = loginDAO.getUser(username);
        if(users.size() > 0){
            password = DigestUtils.md5Hex(password);
            if(users.get(0).getPassword().equals(password)){
                request.getSession().setAttribute("userId",users.get(0).getUserId());
                request.getSession().setAttribute("title",users.get(0).getJob());
                request.getSession().setAttribute("pic",users.get(0).getPicture());
                request.getSession().setAttribute("loggedin",true);
                request.getSession().setAttribute("username",username);
                request.getSession().setAttribute("userrole",users.get(0).getUserrole());
            
                Cookie cookie = new Cookie("userrole",Base64.getEncoder().encodeToString(users.get(0).getUserrole().getBytes()) );
                response.addCookie(cookie);

                String iv = "padding";
                String csrf = username + iv;
                csrf = Base64.getEncoder().encodeToString(csrf.getBytes());
                request.getSession().setAttribute("csrf_token",csrf);
                return "redirect:/dashboard/1";
            }else{
                model.addAttribute("error", "invalid password for username");
            }
        }else{
            model.addAttribute("error", "invalid username");
        }
        return "login/index.html";
      }
}
