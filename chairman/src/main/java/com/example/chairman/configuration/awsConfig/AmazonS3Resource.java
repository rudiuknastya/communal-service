package com.example.chairman.configuration.awsConfig;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class AmazonS3Resource implements Resource {
    private final S3Object s3Object;

    public AmazonS3Resource(S3Object s3Object) {
        this.s3Object = s3Object;
    }

    @Override
    public boolean exists() {
        return s3Object != null;
    }

    @Override
    public URL getURL() throws IOException {
        return s3Object.getObjectContent().getHttpRequest().getURI().toURL();
    }

    @Override
    public URI getURI() throws IOException {
        return s3Object.getObjectContent().getHttpRequest().getURI();
    }

    @Override
    public File getFile() throws IOException {
        return null;
    }

    @Override
    public long contentLength() throws IOException {
        return s3Object.getObjectMetadata().getContentLength();
    }

    @Override
    public long lastModified() throws IOException {
        return s3Object.getObjectMetadata().getLastModified().getTime();
    }

    @Override
    public Resource createRelative(String relativePath) throws IOException {
        return null;
    }

    @Override
    public String getFilename() {
        return s3Object.getKey();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return s3Object.getObjectContent();
    }
}
