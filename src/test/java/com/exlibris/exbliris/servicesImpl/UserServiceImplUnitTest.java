package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        userService.addUser(user);
        Mockito.verify(userDAO).addUser(user);
    }

    @Test
    void shouldGetUser() {
        Mockito.when(userDAO.getUser(user.getId())).thenReturn(userResponse);
        UserResponse response = userService.getUser(user.getId());

        Assertions.assertEquals(userResponse, response);
    }

    @Test
    void shouldThrowUserNotFound() throws IndexOutOfBoundsException {
        Mockito.doThrow(new IndexOutOfBoundsException()).when(userDAO).getUser(user.getId());
        userService.getUser(user.getId());
    }

    @Test
    void shouldGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        Mockito.when(userDAO.getAllUsers()).thenReturn(userList);
        List<User> response = userService.getAllUsers();

        Assertions.assertEquals(userList, response);
    }

    @Test
    void shouldEditUser() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

        userService.editUser(user.getId(), editedUser);
        Mockito.verify(userDAO).editUser(editedUser);
    }

    @Test
    void shouldThrowUserToEditNotFound() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");

        BDDMockito.given(userService.getUser(user.getId())).willThrow(IndexOutOfBoundsException.class);
        Mockito.when(userDAO.getUser(user.getId())).thenThrow(IndexOutOfBoundsException.class);

        userService.editUser(user.getId(), editedUser);
        Mockito.verify(userDAO).editUser(editedUser);
    }

    @Test
    void deleteUser() {
        userService.deleteUser(user.getId());
        Mockito.verify(userDAO).deleteUser(user.getId());
    }
}