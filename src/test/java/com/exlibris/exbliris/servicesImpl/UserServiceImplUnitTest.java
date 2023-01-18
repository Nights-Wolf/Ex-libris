package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.DAO.UserDAO;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock
    private UserService userService;
    @Mock
    private UserDAO userDAO;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private UserResponse userResponse = new UserResponse(1L, "NightsWolf", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void shouldAddUser() {
        Mockito.when(userDAO.addUser(user)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        ResponseEntity<Object> response = userService.addUser(user);
        HttpStatus status = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    void shouldCheckIfUserExists() {
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));

        ResponseEntity<Object> response = userService.addUser(user);
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.CONFLICT, responseStatus);
    }

    @Test
    void shouldGetUser() {
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));
        ResponseEntity<Object> response = userService.getUser(user.getId());
        UserResponse responseUser = (UserResponse) response.getBody();

        Assertions.assertEquals(userResponse, responseUser);
    }

    @Test
    void shouldThrowUserNotFound() {
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(null);

        ResponseEntity<Object> response = userService.getUser(user.getId());
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    void shouldGetAllUsers() {
        List<UserResponse> userList = new ArrayList<>();
        userList.add(userResponse);

        Mockito.when(userDAO.getAllUsers()).thenReturn(new ResponseEntity<>(userList, HttpStatus.OK));
        ResponseEntity<Object> response = userService.getAllUsers();
        List<User> responseUserList = (List<User>) response.getBody();

        Assertions.assertEquals(userList, responseUserList);
    }

    @Test
    void shouldThrowAnyUserNotFound() {
        Mockito.when(userDAO.getAllUsers()).thenReturn(null);

        ResponseEntity<Object> response = userService.getAllUsers();
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    void shouldEditUser() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

        Mockito.when(userDAO.getUser(user.getId())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        Mockito.when(userDAO.editUser(editedUser)).thenReturn(new ResponseEntity<>(editedUser, HttpStatus.OK));
        ResponseEntity<Object> response = userService.editUser(user.getId(), editedUser);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(editedUser, responseUser);
    }

    @Test
    void shouldThrowUserToEditNotFound() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

        Mockito.when(userDAO.getUser(user.getId())).thenReturn(null);

        ResponseEntity<Object> response = userService.editUser(user.getId(), editedUser);
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    void deleteUser() {
        Mockito.when(userDAO.deleteUser(user.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Object> response = userService.deleteUser(user.getId());
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseStatus);
    }

    @Test
    void shouldThrowUserToDeleteNotFound() {
        Mockito.when(userDAO.deleteUser(user.getId())).thenReturn(null);

        ResponseEntity<Object> response = userService.deleteUser(user.getId());
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }
}