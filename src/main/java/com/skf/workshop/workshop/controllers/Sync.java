package com.skf.workshop.workshop.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.skf.workshop.workshop.model.System;



@Controller
public class Sync {
    
    @GetMapping("/sync")
    public String sync(HttpSession session, Model model){
        if(session.getAttribute("userId") == null){
            return "redirect:/login";
        }
        System sys = new System("system_state","Synchronized with App2 Instance");
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);

        String content = "";
        try{
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(sys);
            content = Base64.getEncoder().encodeToString(baos.toByteArray());
        }catch(Exception e){
            e.printStackTrace();
        }

        model.addAttribute("content",content);

        return "sync/index.html";
    }

    @PostMapping("/sync/data")
	public String search(@RequestParam(name="data_obj", required=true) String data_obj, Model model,HttpSession session) {

        try{
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(data_obj)));
            Object obj = ois.readObject();
            if((obj instanceof System)){
                System sys = (System) obj;
                model.addAttribute("content", "{'property':"+sys.getProperty()+", 'value':"+sys.getValue()+"}");
            }else{
                model.addAttribute("content", "Sync error: Not SYSTEM object");
            }
        }catch(Exception e){
            model.addAttribute("content", "Sync error:"+e.getStackTrace());
            return "sync/index.html";
        }
		return "sync/index.html";
	}

}
