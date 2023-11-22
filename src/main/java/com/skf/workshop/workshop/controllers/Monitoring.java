package com.skf.workshop.workshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.HttpSession;

@Controller
public class Monitoring {

    @GetMapping("/monitoring")
    public String monitoring(HttpSession session, Model model, HttpServletRequest request, @CookieValue(value="userrole") String userrole) throws IOException {
        if(null != request.getSession().getAttribute("username") && userrole.equals("YWRtaW4=")){
            String cmdText = "";
            String tasklistCMD = "";
            String userListCMD = "";
            String os = "";
            if (System.getProperty("os.arch").contains("Windows")){
                os = "Windows";
                cmdText = "net user (monitor users)";
                tasklistCMD = "tasklist";
                userListCMD= "net user";
            }else{
                os = "Unix";
                cmdText = "cat /etc/passwd (monitor users)";
                tasklistCMD = "ps aux | grep java";
                userListCMD = "cat /etc/passwd";
            }
           
            model.addAttribute("cmdText", cmdText);
            model.addAttribute("cmdTaskList", runCommand(tasklistCMD,os));
            model.addAttribute("cmdUserList", runCommand(userListCMD,os));

            return "monitoring/index.html";
        }else{
            return "redirect:/dashboard/1";
        }
      }

      public String runCommand(String command, String os) throws IOException{
        Process pb;
        if(os.equals("Windows")){
            pb = new ProcessBuilder("CMD","/C",command).redirectErrorStream(true).start();
        }else{
            pb = new ProcessBuilder("/bin/sh","-c",command).redirectErrorStream(true).start();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(pb.getInputStream()));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line + "\n");
        }
        return sb.toString();
      }
}
