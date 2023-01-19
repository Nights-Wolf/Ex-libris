package com.exlibris.exbliris.DAO;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        userDAO.addUser(user);
        Mockito.verify(userDAO).addUser(user);
    }

    @Test
    void getUser() {
        UserResponse response = userDAO.getUser(user.getId());

        Assertions.assertEquals(userResponse.getId(), response.getId());
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(user);

        List<User> response = userDAO.getAllUsers();

        Assertions.assertEquals(users, response);
    }

    @Test
    void editUser() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Buba", "Całkowksi");
        userDAO.editUser(editedUser);
        Mockito.verify(userDAO).editUser(editedUser);
    }

    @Test
    void deleteUser() {
        userDAO.deleteUser(user.getId());

        UserResponse response = userDAO.getUser(user.getId());

        Assertions.assertEquals(null, response);
    }
}