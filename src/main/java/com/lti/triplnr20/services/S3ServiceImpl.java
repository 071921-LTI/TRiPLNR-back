package com.lti.triplnr20.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3ServiceImpl implements S3Service{

    @Override
    public String upload(MultipartFile file) {
        
        return null;
    }
    
}
