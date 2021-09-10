package com.lti.triplnr20.ControllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lti.triplnr20.controllers.UserController;
import com.lti.triplnr20.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService us;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkIfExistingUserTrue() throws Exception {
        when(us.checkIfExistingUser("")).thenReturn(true);

        mockMvc.perform(get("/users/sub")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
    }

    @Test
    public void checkIfExistingUserFalse() throws Exception {
        when(us.checkIfExistingUser("")).thenReturn(false);

        mockMvc.perform(get("/users/sub")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", ""))
            .andExpect(status().isOk());
    }
}
