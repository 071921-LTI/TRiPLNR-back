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
public class TripControllerTest {
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
    	String token = "token";
		User u1 = new User(1, "user", "first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		
		List<String> sArray = new ArrayList<String>();
		sArray.add("stop1");
		sArray.add("stop2");
		sArray.add("stop3");
		
		List<User> p = new ArrayList<User>();
		p.add(u1);
		p.add(u2);
		p.add(u3);
		
		String d1 = "1111-11-11 11:11:11";
		String d2 = "2021-12-21 12:12:12";
		
		Trip t = new Trip(1, "destination", "origin", "tName", u1, sArray, p, Timestamp.valueOf(d1), Timestamp.valueOf(d2), "spotify", u1, u2, u3);
    	
    	when(us.getUserBySub(token)).thenReturn(u1);
    	when(ts.createTrip(Mockito.any(Trip.class))).thenReturn(t);
    	
    	HttpHeaders h = new HttpHeaders();
    	h.add("Authorization", "token");
    	h.add("StartTime", d1);
    	h.add("EndTime", d2);
		
        mockMvc.perform(post("/trip/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(t))
                .headers(h))
                .andExpect(status().isCreated());
    }
    @Test
    void createTripInvalid() throws Exception {
    	String token = "token";
		User u1 = new User(1, "user", "first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		
		List<String> sArray = new ArrayList<String>();
		sArray.add("stop1");
		sArray.add("stop2");
		sArray.add("stop3");
		
		List<User> p = new ArrayList<User>();
		p.add(u1);
		p.add(u2);
		p.add(u3);
		
		String d1 = "1111-11-11 11:11:11";
		String d2 = "2021-12-21 12:12:12";
		
		Trip t = new Trip(1, "destination", "origin", "tName", u1, sArray, p, Timestamp.valueOf(d1), Timestamp.valueOf(d2), "spotify", u1, u2, u3);
    	
    	when(us.getUserBySub(token)).thenReturn(u1);
    	when(ts.createTrip(Mockito.any(Trip.class))).thenReturn(null);
    	
    	HttpHeaders h = new HttpHeaders();
    	h.add("Authorization", "token");
    	h.add("StartTime", d1);
    	h.add("EndTime", d2);
		
        mockMvc.perform(post("/trip/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(t))
                .headers(h))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateTripValid() throws Exception {
    	String token = "token";
		User u1 = new User(1, "user", "first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		
		List<String> sArray = new ArrayList<String>();
		sArray.add("stop1");
		sArray.add("stop2");
		sArray.add("stop3");
		
		List<User> p = new ArrayList<User>();
		p.add(u1);
		p.add(u2);
		p.add(u3);
		
		String d1 = "1111-11-11 11:11:11";
		String d2 = "2021-12-21 12:12:12";
		
		Trip t = new Trip(1, "destination", "origin", "tName", u1, sArray, p, Timestamp.valueOf(d1), Timestamp.valueOf(d2), "spotify", u1, u2, u3);
    	
    	when(us.getUserBySub(token)).thenReturn(u1);
    	when(ts.updateTrip(Mockito.any(Trip.class))).thenReturn(t);
    	
    	HttpHeaders h = new HttpHeaders();
    	h.add("Authorization", "token");
    	h.add("StartTime", d1);
    	h.add("EndTime", d2);
		
        mockMvc.perform(put("/trip/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(t))
                .headers(h))
                .andExpect(status().isCreated());
    }
    @Test
    void updateTripInvalid() throws Exception {
    	String token = "token";
		User u1 = new User(1, "user", "first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u2 = new User(2, "user2","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		User u3 = new User(3, "user3","first", "last", "address", "pic", "bio", new ArrayList<Trip>(), null);
		
		List<String> sArray = new ArrayList<String>();
		sArray.add("stop1");
		sArray.add("stop2");
		sArray.add("stop3");
		
		List<User> p = new ArrayList<User>();
		p.add(u1);
		p.add(u2);
		p.add(u3);
		
		String d1 = "1111-11-11 11:11:11";
		String d2 = "2021-12-21 12:12:12";
		
		Trip t = new Trip(1, "destination", "origin", "tName", u1, sArray, p, Timestamp.valueOf(d1), Timestamp.valueOf(d2), "spotify", u1, u2, u3);
    	
    	when(us.getUserBySub(token)).thenReturn(u1);
    	when(ts.updateTrip(Mockito.any(Trip.class))).thenReturn(null);
    	
    	HttpHeaders h = new HttpHeaders();
    	h.add("Authorization", "token");
    	h.add("StartTime", d1);
    	h.add("EndTime", d2);
		
        mockMvc.perform(put("/trip/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(t))
                .headers(h))
                .andExpect(status().isOk());
    }
    
    @Test
    void getTripsByUser() throws Exception {
    	List<Trip> t = new ArrayList<Trip>();
        when(us.getTripsByUser("")).thenReturn(t);

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
    	String token = "";
    	User user = new User();
    	List<PassengerRequest> p = new ArrayList<PassengerRequest>();
    	when(us.getUserBySub(token)).thenReturn(user);
        when(ts.getRequestByTo(user)).thenReturn(p);
        
        mockMvc.perform(get("/trip/myrequests")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub(token);
        verify(ts,times(1)).getRequestByTo(user);
    }
    @Test
    void getRequestsNoRequests() throws Exception {
    	String token = "";
    	User user = new User();
    	when(us.getUserBySub(token)).thenReturn(user);
        when(ts.getRequestByTo(user)).thenReturn(null);
        
        mockMvc.perform(get("/trip/myrequests")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
        
        verify(us,times(1)).getUserBySub(token);
        verify(ts,times(1)).getRequestByTo(user);
    }
    
    @Test
    void getTrip() throws Exception {
    	Trip t = new Trip ();
        when(ts.getTripById(1)).thenReturn(t);

        mockMvc.perform(get("/trip/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
    }
    @Test
    void getTripNoTrip() throws Exception {
        when(ts.getTripById(1)).thenReturn(null);

        mockMvc.perform(get("/trip/1")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
    }

}

