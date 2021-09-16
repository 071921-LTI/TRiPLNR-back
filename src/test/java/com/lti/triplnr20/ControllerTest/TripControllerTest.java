package com.lti.triplnr20.ControllerTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.triplnr20.controllers.TripController;
import com.lti.triplnr20.daos.TripRepository;
import com.lti.triplnr20.daos.UserRepository;
import com.lti.triplnr20.models.PassengerRequest;
import com.lti.triplnr20.models.Trip;
import com.lti.triplnr20.models.User;
import com.lti.triplnr20.services.TripService;
import com.lti.triplnr20.services.UserService;

@WebMvcTest(TripController.class)
class TripControllerTest {
    @MockBean
    private TripService ts;
    @MockBean
    private TripRepository tr;
    @MockBean
    private UserService us;
    @MockBean
    private UserRepository ur;
    @MockBean
    Timestamp time;

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
	
	static List<User> mockUsers;
	static List<Trip> mockTrips;
	static List<PassengerRequest> mockPassengerRequests;
	

    
	@BeforeAll
	static void setup() {
		String token = "1:token";
		
		User u1 = new User(1, "user", "first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		
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
    void createTripValid() throws Exception {
    	when(us.getUserBySub(mockToken)).thenReturn(mockUser1);
    	when(ts.createTrip(Mockito.any(Trip.class))).thenReturn(mockTrip);
		
        mockMvc.perform(post("/trip/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mockTrip))
                .headers(mockHeaders))
                .andExpect(status().isCreated());
        
        verify(us,times(1)).getUserBySub(mockToken);
        verify(ts,times(1)).createTrip(Mockito.any(Trip.class));
    }
    @Test
    void createTripInvalid() throws Exception {
    	when(us.getUserBySub(mockToken)).thenReturn(mockUser1);
    	when(ts.createTrip(Mockito.any(Trip.class))).thenReturn(null);
		
        mockMvc.perform(post("/trip/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mockTrip))
                .headers(mockHeaders))
                .andExpect(status().isBadRequest());
        
        verify(us,times(1)).getUserBySub(mockToken);
        verify(ts,times(1)).createTrip(Mockito.any(Trip.class));
    }
    
    @Test
    void updateTripValid() throws Exception {
    	when(us.getUserBySub(mockToken)).thenReturn(mockUser1);
    	when(ts.updateTrip(Mockito.any(Trip.class))).thenReturn(mockTrip);
		
        mockMvc.perform(put("/trip/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mockTrip))
                .headers(mockHeaders))
                .andExpect(status().isCreated());
        
        verify(us,times(1)).getUserBySub(mockToken);
        verify(ts,times(1)).updateTrip(Mockito.any(Trip.class));
    }
    @Test
    void updateTripInvalid() throws Exception {
    	when(us.getUserBySub(mockToken)).thenReturn(mockUser1);
    	when(ts.updateTrip(Mockito.any(Trip.class))).thenReturn(null);
		
        mockMvc.perform(put("/trip/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(mockTrip))
                .headers(mockHeaders))
                .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub(mockToken);
        verify(ts,times(1)).updateTrip(Mockito.any(Trip.class));
    }
    
    @Test
    void getTripsByUser() throws Exception {
        when(us.getTripsByUser("")).thenReturn(mockTrips);

        mockMvc.perform(get("/trip/dashboard")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
    }
    @Test
    void getTripsByUserNoTrips() throws Exception {
        when(us.getTripsByUser("")).thenReturn(null);

        mockMvc.perform(get("/trip/dashboard")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getTripsByUser("");
    }
    
    @Test
    void acceptPassengerRequestValid() throws Exception {
    	PassengerRequest p = new PassengerRequest();
       
        mockMvc.perform(put("/trip/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(p))
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(ts,times(1)).acceptRequest(p);
    }
    @Test
    void acceptPassengerRequestInvalid() throws Exception {
        mockMvc.perform(put("/trip/accept")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void denyPassengerRequestValid() throws Exception {
    	PassengerRequest p = new PassengerRequest();
        mockMvc.perform(put("/trip/deny")
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(p))
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(ts,times(1)).denyRequest(p);
    }
    @Test
    void denyPassengerRequestInvalid() throws Exception {
        mockMvc.perform(put("/trip/deny")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", ""))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void getRequestsValid() throws Exception {
    	when(us.getUserBySub("")).thenReturn(mockUser1);
        when(ts.getRequestByTo(mockUser1)).thenReturn(mockPassengerRequests);
        
        mockMvc.perform(get("/trip/myrequests")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub("");
        verify(ts,times(1)).getRequestByTo(mockUser1);
    }
    @Test
    void getRequestsNoRequests() throws Exception {
    	when(us.getUserBySub("")).thenReturn(mockUser1);
        when(ts.getRequestByTo(mockUser1)).thenReturn(null);
        
        mockMvc.perform(get("/trip/myrequests")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub("");
        verify(ts,times(1)).getRequestByTo(mockUser1);
    }
    
    @Test
    void getTrip() throws Exception {
        when(ts.getTripById(1)).thenReturn(mockTrip);

        mockMvc.perform(get("/trip/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(ts,times(1)).getTripById(1);
    }
    @Test
    void getTripNoTrip() throws Exception {
        when(ts.getTripById(1)).thenReturn(null);

        mockMvc.perform(get("/trip/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(ts,times(1)).getTripById(1);
    }

}

