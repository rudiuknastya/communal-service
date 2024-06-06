package com.example.user.config;


import com.example.user.config.awsConfig.AmazonS3ResourceResolver;
import com.example.user.service.AmazonS3Service;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private final AmazonS3Service amazonS3Service;

    public MvcConfig(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .resourceChain(false)
                .addResolver(new AmazonS3ResourceResolver(amazonS3Service));
    }
}
