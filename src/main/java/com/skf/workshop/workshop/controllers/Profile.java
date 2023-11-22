package com.skf.workshop.workshop.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.skf.workshop.workshop.dao.ProfileDAO;
import com.skf.workshop.workshop.model.User;
import com.skf.workshop.workshop.storage.ImageStorageService;



@Controller
public class Profile {
    @Autowired
    private ProfileDAO profileDAO;

    private final ImageStorageService imageStorageService;

    @Autowired
    public Profile(ImageStorageService imageStorageService){
        this.imageStorageService = imageStorageService;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model, HttpServletRequest request) {
        try{
            List<User> users = profileDAO.getProfile(request.getSession().getAttribute("userId").toString());
            model.addAttribute("user", users.get(0));
            return "profile/index.html";
        }catch(Exception e ){
            return "redirect:/login";
        }
      }

    @GetMapping("/profile/all")
    public String allProfiles(HttpSession session, Model model) {
        List<User> users = profileDAO.getProfiles();
        model.addAttribute("users", users);
        return "profile/all.html";
      }

    @GetMapping("/profile/{id}")
    public String singleProfile(@PathVariable String id, HttpSession session, Model model) {
        List<User> users = profileDAO.getProfile(id);
        model.addAttribute("user", users.get(0));
        return "profile/index.html";
      }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model, HttpServletRequest request) {
        try{
            List<User> users = profileDAO.getProfile(request.getSession().getAttribute("userId").toString());
            model.addAttribute("user", users.get(0));
            return "profile/edit.html";
        }catch(Exception e ){
            return "redirect:/login";
        }
      }

    @PostMapping("/profile/update")
    public String updateProfile(
        @RequestParam(name="name", required=true, defaultValue="") String fullname,
        @RequestParam(name="title", required=true, defaultValue="") String job,
        @RequestParam(name="desc", required=true, defaultValue="") String overview,
        @RequestParam(name="exp", required=true, defaultValue="") String exp, 
        @RequestParam(name="linkedin", required=true, defaultValue="") String linkedin,
        HttpServletRequest request){          
            try{
                job = job.replace("<script>", "");
                profileDAO.updateProfile(Integer.parseInt(request.getSession().getAttribute("userId").toString()),fullname,job,overview,exp,linkedin);
                return "redirect:/profile";
            }catch(Exception e){
                e.printStackTrace();
                return "redirect:/profile/edit";
            }   
    }

    @PostMapping("/profile/uploads")
    public String companyUpload(@RequestParam("file") MultipartFile file, HttpSession session, Model model,HttpServletRequest request) throws IOException{
        try{
            imageStorageService.store(file);
            String fileName = file.getOriginalFilename();
            profileDAO.updatePicture(Integer.parseInt(request.getSession().getAttribute("userId").toString()),fileName);
            model.addAttribute("uploaded", "File was uploaded succesfully to the application.");
            return "profile/edit.html";
        }
        catch(Exception e){
            model.addAttribute("uploaded", "something went wrong, please try again. If the problem is repetitive please contact an administrator!");
            return "profile/edit.html";
        }
    }
    
}

