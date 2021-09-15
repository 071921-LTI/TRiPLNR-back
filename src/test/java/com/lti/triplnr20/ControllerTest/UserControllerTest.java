package com.lti.triplnr20.ControllerTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.triplnr20.controllers.UserController;
import com.lti.triplnr20.exceptions.InvalidAddressException;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.AddressService;
import com.lti.triplnr20.services.S3Service;
import com.lti.triplnr20.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService us;
    @MockBean
    private S3Service s3;

    @Autowired
    private MockMvc mockMvc;
    
    ObjectMapper mapper = new ObjectMapper();
    private String toJson(Object object) {
    	String json = "";
    	try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
    }
    
    @Test
    void getMappingExists() throws Exception {
        mockMvc.perform(get("/users/test")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
    }
    @Test
    void getMappingNotExists() throws Exception {
        mockMvc.perform(get("/users/te")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateValid() throws Exception {
    	User u = new User(1, "user", "first", "last", "", "", "address", null, null);
        when(us.getUserById(1)).thenReturn(u);
        when(us.updateUser(u)).thenReturn("Successful");

        mockMvc.perform(put("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
          	.content(toJson(u))
            .header("Authorization", "1:token"))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserById(1);
        verify(us,times(1)).updateUser(u);
    }
    @Test
    void updateInvalid() throws Exception {
    	User u = new User(1, "user", "first", "last", "", "", "address", null, null);
        when(us.getUserById(1)).thenReturn(u);
        when(us.updateUser(u)).thenThrow(InvalidAddressException.class);

        mockMvc.perform(put("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
          	.content(toJson(u))
            .header("Authorization", "1:token"))
            .andExpect(status().isBadRequest());
        
        verify(us,times(1)).getUserById(1);
        verify(us,times(1)).updateUser(u);
    }
    

	@Test
    void createValid() throws Exception {
    	List<Trip> trips = new ArrayList<Trip>();
    	List<User> users = new ArrayList<User>();
    	User u = new User(1, "sub", "first", "last", "pic", "bio", "address", trips, users);
    	
    	MockMultipartFile user = new MockMultipartFile(
    			"user", 
    			"user.txt", 
    			"application/json", 
    			toJson(u).getBytes());
    	MockMultipartFile file = new MockMultipartFile(
    			"file", 
    			"file.txt", 
    			"text/blank", 
    			"some xml".getBytes());

        when(us.createUser(u)).thenReturn(u);
        when(s3.upload(file)).thenReturn("pic");

        mockMvc.perform(multipart("/users/create")
        		.file(file)
                .file(user)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .header("Authorization", "1:token"))
            .andExpect(status().isCreated());
        
        verify(us,times(1)).createUser(u);
        verify(s3,times(1)).upload(file);
    }
	@Test
    void createInvalid() throws Exception {
    	List<Trip> trips = new ArrayList<Trip>();
    	List<User> users = new ArrayList<User>();
    	User u = new User(1, "sub", "first", "last", null, "bio", "address", trips, users);
    	
    	MockMultipartFile user = new MockMultipartFile(
    			"user", 
    			"user.txt", 
    			"application/json", 
    			toJson(u).getBytes());
    	MockMultipartFile file = new MockMultipartFile(
    			"file", 
    			"file.txt", 
    			"text/blank", 
    			"some xml".getBytes());

        when(us.createUser(u)).thenReturn(u);
        when(s3.upload(file)).thenThrow(IOException.class);

        mockMvc.perform(multipart("/users/create")
        		.file(file)
                .file(user)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .header("Authorization", "1:token"))
            .andExpect(status().isIAmATeapot());
    }
    
    @Test
    void getByIdExists() throws Exception {
        when(us.getUserById(1)).thenReturn(new User());

        mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserById(1);
    }
    @Test
    void getByIdNotExists() throws Exception {
        when(us.getUserById(1)).thenReturn(null);

        mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserById(1);
    }

    @Test
    void getUserBySubExists() throws Exception {
        when(us.getUserBySub("")).thenReturn(new User());

        mockMvc.perform(get("/users/sub")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub("");
    }
    @Test
    void getUserBySubNotExists() throws Exception {
        when(us.getUserBySub("")).thenReturn(null);

        mockMvc.perform(get("/users/sub")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub("");
    }
    
    @Test
    void getByUserExists() throws Exception {
        when(us.getUserBySub("")).thenReturn(new User());

        mockMvc.perform(get("/users/user")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub("");
    }
    @Test
    void getByUserNotExists() throws Exception {
        when(us.getUserBySub("")).thenReturn(null);

        mockMvc.perform(get("/users/user")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub("");
    }
    
    @Test
    void getFriendsExists() throws Exception {
    	List<User> friends = new ArrayList<User>();
        when(us.getFriends("")).thenReturn(friends);

        mockMvc.perform(get("/users/myfriends")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getFriends("");
    }
    @Test
    void getFriendsNotExists() throws Exception {
        when(us.getFriends("")).thenReturn(null);

        mockMvc.perform(get("/users/myfriends")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getFriends("");
    }
    
    @Test
    void getProfilesExists() throws Exception {
    	List<User> profiles = new ArrayList<User>();
        when(us.getProfiles("")).thenReturn(profiles);

        mockMvc.perform(get("/users/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getProfiles("");
    }
    @Test
    void getProfilesNotExists() throws Exception {
        when(us.getProfiles("")).thenReturn(null);

        mockMvc.perform(get("/users/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getProfiles("");
    }
}
