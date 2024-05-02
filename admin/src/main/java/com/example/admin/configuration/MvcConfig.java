package com.example.admin.configuration;

import com.example.admin.configuration.awsConfig.AmazonS3ResourceResolver;
import com.example.admin.service.AmazonS3Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    private final AmazonS3Service amazonS3Service;

    public MvcConfig(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploads/**")
                .resourceChain(false)
                .addResolver(new AmazonS3ResourceResolver(amazonS3Service));
    }
}
