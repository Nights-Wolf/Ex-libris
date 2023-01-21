package com.exlibris.exbliris.api;

import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserRestController.class)
class UserRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user = new User(1L, "NightsWolf", "123", "dawinavi@wp.pl", "Dawid", "Całkowksi");
    private UserResponse userResponse = new UserResponse(1L, "NightsWolf", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    void shouldCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(userService).addUser(Mockito.any(User.class));
    }

    @Test
    void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetUser() throws Exception {
        Mockito.when(userService.getUser(1L)).thenReturn(userResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService).getUser(1L);
    }

    @Test
    void shouldThrowUserNotFound() throws Exception {
        Mockito.when(userService.getUser(2L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(userService).getUser(2L);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Mockito.when(userService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService).getAllUsers();
    }

    @Test
    void shouldEditUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(userService).editUser(Mockito.eq(1L), Mockito.any(User.class));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(userService).deleteUser(1L);
    }
}