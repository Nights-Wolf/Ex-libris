package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.DB.UserDB;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
    private UserDB userDB;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private UserResponse userResponse = new UserResponse(1L, "NightsWolf", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDB);
    }

    @Test
    void shouldAddUser() {
        Mockito.when(userDB.addUser(user)).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        ResponseEntity<Object> response = userService.addUser(user);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(user, responseUser);
    }

    @Test
    void shouldCheckIfUserExists() {
        Mockito.when(userDB.getUser(user.getId())).thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));

        ResponseEntity<Object> response = userService.addUser(user);
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.CONFLICT, responseStatus);
    }

    @Test
    void shouldGetUser() {
        Mockito.when(userDB.getUser(user.getId())).thenReturn(new ResponseEntity<>(userResponse, HttpStatus.OK));
        ResponseEntity<Object> response = userService.getUser(user.getId());
        UserResponse responseUser = (UserResponse) response.getBody();

        Assertions.assertEquals(userResponse, responseUser);
    }

    @Test
    void shouldThrowUserNotFound() {
        Mockito.when(userDB.getUser(user.getId())).thenReturn(null);

        ResponseEntity<Object> response = userService.getUser(user.getId());
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    void shouldGetAllUsers() {
        List<UserResponse> userList = new ArrayList<>();
        userList.add(userResponse);

        Mockito.when(userDB.getAllUsers()).thenReturn(new ResponseEntity<>(userList, HttpStatus.OK));
        ResponseEntity<Object> response = userService.getAllUsers();
        List<User> responseUserList = (List<User>) response.getBody();

        Assertions.assertEquals(userList, responseUserList);
    }

    @Test
    void shouldThrowAnyUserNotFound() {
        Mockito.when(userDB.getAllUsers()).thenReturn(null);

        ResponseEntity<Object> response = userService.getAllUsers();
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    void shouldEditUser() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

        Mockito.when(userDB.getUser(user.getId())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));
        Mockito.when(userDB.editUser(editedUser)).thenReturn(new ResponseEntity<>(editedUser, HttpStatus.OK));
        ResponseEntity<Object> response = userService.editUser(user.getId(), editedUser);
        User responseUser = (User) response.getBody();

        Assertions.assertEquals(editedUser, responseUser);
    }

    @Test
    void shouldThrowUserToEditNotFound() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

        Mockito.when(userDB.getUser(user.getId())).thenReturn(null);

        ResponseEntity<Object> response = userService.editUser(user.getId(), editedUser);
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }

    @Test
    void deleteUser() {
        Mockito.when(userDB.deleteUser(user.getId())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Object> response = userService.deleteUser(user.getId());
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseStatus);
    }

    @Test
    void shouldThrowUserToDeleteNotFound() {
        Mockito.when(userDB.deleteUser(user.getId())).thenReturn(null);

        ResponseEntity<Object> response = userService.deleteUser(user.getId());
        HttpStatus responseStatus = (HttpStatus) response.getStatusCode();

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseStatus);
    }
}