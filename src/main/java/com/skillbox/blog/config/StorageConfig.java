package com.skillbox.blog.config;

import com.skillbox.blog.service.ImageService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StorageConfig implements WebMvcConfigurer, CommandLineRunner {

  @Getter
  private String location = "upload";

  @Autowired
  ImageService imageService;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(location + "/*")
        .addResourceLocations("file:" + location + "/");
  }

  @Override
  public void run(String... args) throws Exception {
    imageService.init();
  }
}
