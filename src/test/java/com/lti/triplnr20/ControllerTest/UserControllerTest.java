package com.lti.triplnr20.ControllerTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.triplnr20.controllers.UserController;
import com.lti.triplnr20.exceptions.InvalidAddressException;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.S3Service;
import com.lti.triplnr20.services.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService us;
    @MockBean
    private S3Service s3;

    @Autowired
    private MockMvc mockMvc;
    
    //Mock objects/variables
    static String mockToken;
    
	static User mockUser1;
	static User mockUser2;
	static User mockUser3;
	
	static Trip mockTrip;
	
	static String mockDate1;
	static String mockDate2;
	
	static HttpHeaders mockHeaders;
	
	static String mockUserJson;
	
	static MockMultipartFile mockFile;
	static MockMultipartFile mockUserFile;
	
	static List<User> mockUsers;
	static List<Trip> mockTrips;
	static List<PassengerRequest> mockPassengerRequests;
	

    
	@BeforeAll
	static void setup() {
		String token = "1:token";
		
		User u1 = new User(1, "user", "first", "last", "pic", "bio", "address", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "pic", "bio", "address", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "pic", "bio", "address", new ArrayList<Trip>(), null);
		
		String d1 = "1111-11-11 11:11:11";
		String d2 = "2021-12-21 12:12:12";
		
		List<String> sArray = new ArrayList<String>();
		sArray.add("stop1");
		sArray.add("stop2");
		sArray.add("stop3");
		
		mockUsers = new ArrayList<>();
		mockUsers.add(u1);
		mockUsers.add(u2);
		mockUsers.add(u3);
		
		Trip t = new Trip(1, "destination", "origin", "tName", u1, sArray, mockUsers, Timestamp.valueOf(d1), Timestamp.valueOf(d2), "spotify", u1, u2, u3);
		
		mockTrip = t;
		
		mockUser1 = u1;
		mockUser2 = u2;
		mockUser3 = u3;
		
		mockDate1 = d1;
		mockDate2 = d2;
		mockToken = token;
		
		mockTrips = new ArrayList<Trip>();
		mockPassengerRequests = new ArrayList<PassengerRequest>();
		mockHeaders = new HttpHeaders();
		mockHeaders.add("Authorization", mockToken);
		mockHeaders.add("StartTime", mockDate1);
		mockHeaders.add("EndTime", mockDate2);
		
		ObjectMapper mapper = new ObjectMapper();
    	try {
    		mockUserJson = mapper.writeValueAsString(mockUser1);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mockUserFile = new MockMultipartFile(
    			"user", 
    			"user.txt", 
    			"application/json", 
    			mockUserJson.getBytes());
    	mockFile = new MockMultipartFile(
    			"file", 
    			"file.txt", 
    			"text/blank", 
    			"some xml".getBytes());
	}
    
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
        mockMvc.perform(get("/users/wrong")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateValid() throws Exception {
        when(us.getUserById(1)).thenReturn(mockUser1);
        when(us.updateUser(mockUser1)).thenReturn("Successful");

        mockMvc.perform(put("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
          	.content(toJson(mockUser1))
            .header("Authorization", mockToken))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserById(1);
        verify(us,times(1)).updateUser(mockUser1);
    }
    @Test
    void updateInvalid() throws Exception {
        when(us.getUserById(1)).thenReturn(mockUser1);
        when(us.updateUser(mockUser1)).thenThrow(InvalidAddressException.class);

        mockMvc.perform(put("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
          	.content(toJson(mockUser1))
            .header("Authorization", mockToken))
            .andExpect(status().isBadRequest());
        
        verify(us,times(1)).getUserById(1);
        verify(us,times(1)).updateUser(mockUser1);
    }
    

	@Test
    void createValid() throws Exception {
        when(us.createUser(mockUser1)).thenReturn(mockUser1);
        when(s3.upload(mockFile)).thenReturn("pic");

        mockMvc.perform(multipart("/users/create")
        		.file(mockFile)
                .file(mockUserFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .header("Authorization", mockToken))
            .andExpect(status().isCreated());
        
        verify(us,times(1)).createUser(mockUser1);
        verify(s3,times(1)).upload(mockFile);
    }
	@Test
    void createInvalid() throws Exception {
        when(us.createUser(mockUser1)).thenReturn(mockUser1);
        when(s3.upload(mockFile)).thenThrow(IOException.class);

        mockMvc.perform(multipart("/users/create")
        		.file(mockFile)
                .file(mockUserFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .header("Authorization", mockToken))
            .andExpect(status().isIAmATeapot());
    }
    
    @Test
    void getByIdExists() throws Exception {
        when(us.getUserById(1)).thenReturn(mockUser1);

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
        when(us.getUserBySub("")).thenReturn(mockUser1);

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
        when(us.getUserBySub("")).thenReturn(mockUser1);

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
        when(us.getFriends("")).thenReturn(mockUsers);

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
        when(us.getProfiles("")).thenReturn(mockUsers);

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
