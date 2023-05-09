package com.example.travel_agency_pfe.Configurations;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MVCConfig implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        exposeDirectory("travel-images",registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir =  Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/"+dirName+"/**").addResourceLocations("file:/"+uploadPath+"/");
    }
}
