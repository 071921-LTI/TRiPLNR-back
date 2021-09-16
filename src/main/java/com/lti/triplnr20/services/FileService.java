package com.lti.triplnr20.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class FileService {
	
	@Autowired
	public FileService() {
		super();
	}
    
	public String generateUUID() {
		return UUID.randomUUID().toString();
    }
	
	public InputStream getInputStream(MultipartFile file) throws IOException{
		return file.getInputStream();
    }
	
	public ObjectMetadata getObjectMetadata() {
		return new ObjectMetadata();
    }
}
