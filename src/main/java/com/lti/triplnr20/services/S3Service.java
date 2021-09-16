package com.lti.triplnr20.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String upload(MultipartFile file) throws IOException;
}
