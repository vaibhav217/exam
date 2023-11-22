
package com.skf.workshop.workshop;

import java.io.File;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;



@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = new File(".").getAbsolutePath();     
        registry.addResourceHandler("/images/**","/css/**","/fonts/**","/js/**","/lib/**").addResourceLocations("file:"+absolutePath+"/images/","classpath:/static/css/","classpath:/static/fonts/","classpath:/static/js/","classpath:/static/lib/");
    }

}
