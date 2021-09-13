package com.lti.triplnr20.services;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    
    String upload(MultipartFile file);

}
