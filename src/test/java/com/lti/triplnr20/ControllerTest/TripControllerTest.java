package com.lti.triplnr20.ControllerTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.lti.triplnr20.controllers.TripController;
import com.lti.triplnr20.services.TripService;

@WebMvcTest(TripController.class)
public class TripControllerTest {
    @MockBean
    private TripService us;

    @Autowired
    private MockMvc mockMvc;

}
