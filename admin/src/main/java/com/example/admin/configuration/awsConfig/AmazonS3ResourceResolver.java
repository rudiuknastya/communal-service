package com.example.admin.configuration.awsConfig;

import com.amazonaws.services.s3.model.S3Object;
import com.example.admin.service.AmazonS3Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import java.util.List;
public class AmazonS3ResourceResolver implements ResourceResolver {
    private final AmazonS3Service amazonS3Service;

    public AmazonS3ResourceResolver(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
        S3Object s3Object = amazonS3Service.getS3Object(requestPath);
        return new AmazonS3Resource(s3Object);
    }

    @Override
    public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
        return null;
    }
}
