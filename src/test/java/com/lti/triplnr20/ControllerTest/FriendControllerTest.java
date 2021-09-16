package com.lti.triplnr20.ControllerTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.triplnr20.controllers.FriendController;
import com.lti.triplnr20.models.FriendRequest;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.FriendService;
import com.lti.triplnr20.services.UserService;

@WebMvcTest(FriendController.class)
public class FriendControllerTest {
    @MockBean
    private FriendService fs;
    @MockBean
    private UserService us;

    @Autowired
    private MockMvc mockMvc;
    
    //Mocked variables and objects
    static User mockUser;
    
    static FriendRequest mockRequest;
    
    static String mockToken;
   
    static List<FriendRequest> mockRequests;
	
	@BeforeAll
	static void setup() {
		mockUser = new User();
		
		mockRequest = new FriendRequest();
		
		mockToken = "1:token";
		
		mockRequests = new ArrayList<FriendRequest>();
		
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
    void getFriendRequestsExists() throws Exception {
    	when(us.getUserBySub(mockToken)).thenReturn(mockUser);
        when(fs.getRequestsByUser(mockUser)).thenReturn(mockRequests);

        mockMvc.perform(get("/friends/myrequests")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", mockToken))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub(mockToken);
        verify(fs,times(1)).getRequestsByUser(mockUser);
    }
    @Test
    void getFriendRequestsNotExists() throws Exception {
    	when(us.getUserBySub(mockToken)).thenReturn(mockUser);
        when(fs.getRequestsByUser(mockUser)).thenReturn(null);

        mockMvc.perform(get("/friends/myrequests")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", mockToken))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub(mockToken);
        verify(fs,times(1)).getRequestsByUser(mockUser);
    }
    
    @Test
    void makeRequestValid() throws Exception {
        when(fs.makeRequest(mockRequest)).thenReturn(mockRequest);

        mockMvc.perform(post("/friends/newrequest")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(mockRequest))
            .header("Authorization", mockToken))
            .andExpect(status().isCreated());
        
        verify(fs,times(1)).makeRequest(mockRequest);
    }
    @Test
    void makeRequestInvalid() throws Exception {
        when(fs.makeRequest(mockRequest)).thenReturn(null);

        mockMvc.perform(post("/friends/newrequest")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(null))
            .header("Authorization", mockToken))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void acceptRequestValid() throws Exception {
        mockMvc.perform(put("/friends/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(mockRequest))
            .header("Authorization", mockToken))
            .andExpect(status().isOk());
        
        verify(fs,times(1)).acceptRequest(mockRequest);
    }
    @Test
    void acceptRequestInvalid() throws Exception {
        mockMvc.perform(put("/friends/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(null))
            .header("Authorization", mockToken))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void denyRequestValid() throws Exception {
        mockMvc.perform(put("/friends/deny")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(mockRequest))
            .header("Authorization", mockToken))
            .andExpect(status().isOk());
        
        verify(fs,times(1)).denyRequest(mockRequest);
    }
    @Test
    void denyRequestInvalid() throws Exception {
        mockMvc.perform(put("/friends/deny")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(null))
            .header("Authorization", mockToken))
            .andExpect(status().isBadRequest());
    }
}
