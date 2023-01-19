package com.exlibris.exbliris.api;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserRestController.class)
class UserRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    private User user = new User(1L, "NightsWolf", "123", "dawinavi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        userDAO.addUser(user);
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
        Mockito.when(userService.getUser(1L)).thenReturn(userDAO.getUser(1L));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService).getUser(1L);
    }

    @Test
    void shouldThrowUserNotFound() throws Exception {
        Mockito.when(userService.getUser(2L)).thenReturn(userDAO.getUser(2L));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(userService).getUser(2L);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(userDAO.getAllUsers());
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