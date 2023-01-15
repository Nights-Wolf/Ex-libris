package com.exlibris.exbliris.DB;

import com.exlibris.exbliris.models.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserDBTest {

    @MockBean
    private UserDB userDB;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userDB = new UserDB();
    }

    @Test
    void addUser() {
        ResponseEntity<Object> response = userDB.addUser(user);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void getUser() {
        userDB.addUser(user);

        ResponseEntity<Object> response = userDB.getUser(user.getId());
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void getAllUsers() {
        userDB.addUser(user);

        List<User> users = new ArrayList<>();
        users.add(user);

        ResponseEntity<Object> response = userDB.getAllUsers();
        List<User> responseList = (List<User>) response.getBody();

        Assertions.assertEquals(users, responseList);
    }

    @Test
    void editUser() {
        userDB.addUser(user);

        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Buba", "Całkowksi");
        ResponseEntity<Object> response = userDB.editUser(editedUser);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void deleteUser() {
        userDB.addUser(user);

        userDB.deleteUser(user.getId());

        ResponseEntity<Object> response = userDB.getUser(user.getId());
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(null, responseUser);
    }
}