package com.lti.triplnr20.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;

@SpringBootTest(classes= S3ServiceImpl.class)
public class S3ServiceTest {
	
	@Autowired
	private S3Service ss;
	
	@MockBean
	AmazonS3 mockS3;
	
	@MockBean
    FileService mockFls;
	
	//Mocked variables and objects
	static URL mockURL;
	
	static ObjectMetadata mockObjectMetaData;
	
	static InputStream mockInputStream;
	
	static String mockUUIDstring;
	
	static MockMultipartFile mockFile;
	
	@BeforeEach
	void setup() throws IOException {
    	mockFile = new MockMultipartFile(
    			"file", 
    			"file.txt", 
    			"text/blank", 
    			"some xml".getBytes());
    	
    	mockObjectMetaData = new ObjectMetadata();
    	
    	mockUUIDstring = "UUID";
    	
    	mockInputStream = mockFile.getInputStream();
    	
    	mockURL = new URL("https://triplnr-images.s3.us-east-2.amazonaws.com/7c805567-62af-4c4a-9b5a-70f6f9b70475");
	}
	
	@Test
	void upload() throws IOException {
		when(mockFls.getInputStream(mockFile)).thenReturn(mockInputStream);
		when(mockFls.getObjectMetadata()).thenReturn(mockObjectMetaData);
		when(mockFls.generateUUID()).thenReturn(mockUUIDstring);
		when(mockS3.getUrl(Mockito.anyString(), Mockito.anyString())).thenReturn(mockURL);
		when(mockS3.putObject(
				"triplnr-images", 
				mockUUIDstring, 
				mockInputStream, 
				mockObjectMetaData))
		.thenReturn(new PutObjectResult());
		
		assertEquals("https://triplnr-images.s3.us-east-2.amazonaws.com/7c805567-62af-4c4a-9b5a-70f6f9b70475", 
				ss.upload(mockFile));
		verify(mockS3,times(1)).putObject(
				"triplnr-images", 
				mockUUIDstring, 
				mockInputStream, 
				mockObjectMetaData);
	}
}

	
	
	
	
	
	
