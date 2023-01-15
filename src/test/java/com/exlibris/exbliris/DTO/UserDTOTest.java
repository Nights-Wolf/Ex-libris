package com.exlibris.exbliris.DTO;

import com.exlibris.exbliris.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDTOTest {

    @MockBean
    private UserDTO userDTO;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
    }

    @Test
    void addUser() {
        ResponseEntity<Object> response = userDTO.addUser(user);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void getUser() {
        userDTO.addUser(user);

        ResponseEntity<Object> response = userDTO.getUser(user.getId());
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void getAllUsers() {
        userDTO.addUser(user);

        List<User> users = new ArrayList<>();
        users.add(user);

        ResponseEntity<Object> response = userDTO.getAllUsers();
        List<User> responseList = (List<User>) response.getBody();

        Assertions.assertEquals(users, responseList);
    }

    @Test
    void editUser() {
        userDTO.addUser(user);

        User editedUser = User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Buba", "Całkowksi");
        ResponseEntity<Object> response = userDTO.editUser(editedUser);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void deleteUser() {
        userDTO.addUser(user);

        userDTO.deleteUser(user.getId());

        ResponseEntity<Object> response = userDTO.getUser(user.getId());
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(null, responseUser);
    }
}