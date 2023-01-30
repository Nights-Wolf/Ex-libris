package com.exlibris.exbliris.api;

import com.exlibris.exbliris.database.LibraryRepository;
import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.Users;
import com.exlibris.exbliris.services.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebMvcTest(LibraryRestController.class)
class LibraryRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private LibraryRepository libraryRepository;

    @MockBean
    private UserRepository userRepository;

    private Users users = new Users(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private Library library = new Library(1L, "Bokshelf", users, "kaowpdkawd", new Date());

    @BeforeEach
    void setUp() {
        userRepository.save(users);
        libraryRepository.save(library);
    }

    @Test
    void shouldCreateLibrary() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/library")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(library)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(libraryService).createLibrary(Mockito.any(Library.class));
    }

    @Test
    void shouldReturnBadRequest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/library")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldGetLibrary() throws Exception{
        Mockito.when(libraryService.getLibrary(1L)).thenReturn(library);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/library/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(libraryService).getLibrary(1L);
    }

    @Test
    void shouldThrowUserNotFound() throws Exception {
        Mockito.when(libraryService.getLibrary(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/library/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(libraryService).getLibrary(1L);
    }

    @Test
    void shouldReturnAllLibraries() throws Exception {
        List<Library> libraries = new ArrayList<>();
        libraries.add(library);
        Mockito.when(libraryService.getAllLibraries()).thenReturn(libraries);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/library/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(libraryService).getAllLibraries();
    }

    @Test
    void shouldReturnLibrariesByUserId() throws Exception {
        List<Library> libraries = new ArrayList<>();
        libraries.add(library);
        Mockito.when(libraryService.getByUser(users.getId())).thenReturn(libraries);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/library/getByUser/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(libraryService).getByUser(users.getId());
    }

    @Test
    void shouldEditLibrary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/library/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(library)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(libraryService).editLibrary(Mockito.eq(1L), Mockito.any(Library.class));
    }

    @Test
    void shouldDeleteLibrary() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/library/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(libraryService).deleteLibrary(1L);
    }
}