package com.exlibris.exbliris.DAO;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserDAOTest {

    @Mock
    private UserDAO userDAO;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private User user2 = new User(2L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private UserResponse userResponse = new UserResponse(1L, "NightsWolf", "dawi@wp.pl", "Dawid", "Całkowksi");


    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        userDAO.addUser(user);
    }

    @Test
    void addUser() {
        ResponseEntity<Object> response = userDAO.addUser(user2);

        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.CREATED, responseStatus);
    }

    @Test
    void getUser() {
        ResponseEntity<Object> response = userDAO.getUser(user.getId());
        UserResponse responseUser = (UserResponse) response.getBody();

        Assertions.assertEquals(userResponse.getId(), responseUser.getId());
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        ResponseEntity<Object> response = userDAO.getAllUsers();
        List<User> responseList = (List<User>) response.getBody();

        Assertions.assertEquals(users, responseList);
    }

    @Test
    void editUser() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Buba", "Całkowksi");
        ResponseEntity<Object> response = userDAO.editUser(editedUser);
        HttpStatus responseStatusCode = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseStatusCode);
    }

    @Test
    void deleteUser() {
        userDAO.addUser(user);

        userDAO.deleteUser(user.getId());

        ResponseEntity<Object> response = userDAO.getUser(user.getId());
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(null, responseUser);
    }
}