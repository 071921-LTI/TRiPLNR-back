package com.lti.triplnr20.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3ServiceImpl implements S3Service{

    private AmazonS3 s3;
    
    private FileService fls;
    
	@Autowired
	public S3ServiceImpl(AmazonS3 s3, FileService fls) {
		super();
		this.s3 = s3;
		this.fls = fls;
	}
    
    @Override
    public String upload(MultipartFile file) throws IOException {
        
        InputStream inputStream = fls.getInputStream(file);

        ObjectMetadata objectMetadata = fls.getObjectMetadata();

        String uuid = fls.generateUUID();

        s3.putObject("triplnr-images", uuid, inputStream, objectMetadata);
        
        URL imageUrl = s3.getUrl("triplnr-images", uuid);

        return imageUrl.toString();
    }
 
}
