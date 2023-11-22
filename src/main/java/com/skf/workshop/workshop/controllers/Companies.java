package com.skf.workshop.workshop.controllers;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.skf.workshop.workshop.storage.StorageService;


@Controller
public class Companies {

    private final StorageService storageService;

    @Autowired
    public Companies(StorageService storageService) {
      this.storageService = storageService;
    }

    @GetMapping("/companies")
	public String companies(HttpSession session) {
        return session.getAttribute("userId") == null ? "redirect:/login" : "companies/index.html";
	}

    @GetMapping("/companies/new")
	public String addCompany(HttpSession session) {
        return session.getAttribute("userId") == null ? "redirect:/login" : "companies/new.html";
	}

    @PostMapping("/companies/getXML")
	public ResponseEntity<Resource> xmlTemplate(@RequestParam(name="example_file", required=true, defaultValue="company.xml") String filename, HttpSession session) throws IOException, Exception{       
        if(filename.startsWith("/etc")) throw new Exception("The path can't start with /etc");
        if(filename.startsWith("/skf")) throw new Exception("The path can't start with /skf");
        if(filename.contains("pom.xml")) throw new Exception("Application file pom.xml access is forbidden");
        if(filename.contains("Dockerfile")) throw new Exception("Application file Dockerfile access is forbidden");
        if(filename.contains("Database.db")) throw new Exception("Application file Dockerfile access is forbidden");

        filename = filename.replace("../", "");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))){       
            StringBuilder content = new StringBuilder();
            String line;
            while((line = br.readLine())!= null){
                content.append(line);
            }

            ByteArrayResource resource = new ByteArrayResource(content.toString().getBytes());
            HttpHeaders httpHeader = new HttpHeaders();
            httpHeader.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
            return ResponseEntity.ok().headers(httpHeader).contentLength(content.toString().getBytes().length).contentType(MediaType.parseMediaType("text/html")).body(resource);

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

    @PostMapping("/company/upload")
    public String companyUpload(@RequestParam("company") MultipartFile file, HttpSession session, Model model) throws IOException{
        if (session.getAttribute("userId") == null) return "redirect:/login";
        String[] blockedExtensions = {".exe", ".py", ".js", ".jar",".png" ,".jpeg",".jpg",".gif",".txt",".properties",".java",".sql",".class",".html",".css",".com",".bat",".mp4",".json", ".sql"};
        for(String extension : blockedExtensions){
            if(file.getOriginalFilename().endsWith(extension)){
                model.addAttribute("error", "File extension not allowed");
                return "companies/new.html";
            }
        }
        if(file.getSize() > 10000000){
            model.addAttribute("error", "File size too big");
            return "companies/new.html";
        }
        storageService.store(file);
        String content = new String(file.getBytes());
        String nodes = "";
        try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(content.getBytes());
			org.w3c.dom.Document doc = builder.parse(is);
			nodes = doc.getDocumentElement().getTextContent();
		} catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
			e.printStackTrace();
            model.addAttribute("error", "Validation failed");
            return "companies/new.html";
		}
        model.addAttribute("nodes", nodes);
        
        return "companies/new.html";
     
    }

    
    
}
