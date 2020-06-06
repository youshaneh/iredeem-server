package com.shane.iredeem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
              .allowedOrigins("http://114.36.11.169:3000")
              .allowCredentials(true)
              .allowedMethods("GET", "POST", "DELETE", "PUT")
              .maxAge(24 * 60 * 60);
  }
}