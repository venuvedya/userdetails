package com.userapplication.testcases;

import com.userapplication.model.User;
import com.userapplication.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {
        // Mock the userService.getAllUsers() method
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        // Perform GET request to /api/users and assert response
        mockMvc.perform(get("http://localhost:8080/api/users"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // Write more tests for other functionalities...

    @Test
    public void testCreateUser() throws Exception {
        // Mock the userService.createUser() method
        User newUser = new User("John", "Doe", "john.doe@example.com", "ACTIVE", "1234567890", "1990-01-01", "123 Main St");
        when(userService.createUser(any(User.class))).thenReturn(newUser);

        // Perform POST request to /api/users with user data
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"status\": \"ACTIVE\", \"phoneNumber\": \"1234567890\", \"dateOfBirth\": \"1990-01-01\", \"address\": \"123 Main St\" }"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Mock the userService.updateUser() method
        User updatedUser = new User("John", "Doe", "john.doe@example.com", "ACTIVE", "1234567890", "1990-01-01", "123 Main St");
        when(userService.updateUser(Long.valueOf(eq("userId")), any(User.class))).thenReturn(updatedUser);

        // Perform PUT request to /api/users/userId with updated user data
        mockMvc.perform(put("/api/users/userId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"status\": \"ACTIVE\", \"phoneNumber\": \"1234567890\", \"dateOfBirth\": \"1990-01-01\", \"address\": \"123 Main St\" }"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Perform DELETE request to /api/users/userId and assert response
        mockMvc.perform(delete("/api/users/userId"))
                .andExpect(status().isOk());
    }
}