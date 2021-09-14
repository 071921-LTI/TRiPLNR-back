package com.lti.triplnr20.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.lti.triplnr20.config.AmazonConfig;

@Service
public class S3ServiceImpl implements S3Service{

    private final AmazonS3 s3 = new AmazonConfig().s3();

    @Override
    public String upload(MultipartFile file) throws IOException {
        
        InputStream inputStream = file.getInputStream();

        ObjectMetadata objectMetadata = new ObjectMetadata();

        String uuid = UUID.randomUUID().toString();

        s3.putObject("triplnr-images", uuid, inputStream, objectMetadata);
        
        URL imageUrl = s3.getUrl("triplnr-images", uuid);

        return imageUrl.toString();
    }
    
}
