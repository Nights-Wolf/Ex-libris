package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private UserResponse userResponse = new UserResponse(1L, "NightsWolf", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldAddUser() {
        userService.addUser(user);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        User response = userArgumentCaptor.getValue();

        Assertions.assertEquals(user, response);
    }

    @Test
    void shouldGetUser() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        UserResponse response = userService.getUser(user.getId());

        Assertions.assertEquals(userResponse.getId(), response.getId());
    }

    @Test
    void shouldThrowUserNotFound() {
        Mockito.when(userRepository.findById(user.getId())).thenThrow(HttpClientErrorException.class);
        Assertions.assertThrows(HttpClientErrorException.class, () -> userService.getUser(user.getId()));
    }

    @Test
    void shouldGetAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> response = userService.getAllUsers();

        Assertions.assertEquals(userList, response);
    }

    @Test
    void shouldEditUser() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

        userService.editUser(user.getId(), editedUser);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User response = userArgumentCaptor.getValue();

        Assertions.assertEquals(editedUser, response);
    }

    @Test
    void shouldThrowUserToEditNotFound() {
        User editedUser = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
        Mockito.when(userRepository.findById(user.getId())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> userService.editUser(user.getId(), editedUser));
    }

    @Test
    void deleteUser() {
        userService.deleteUser(user.getId());
        Mockito.verify(userRepository).deleteById(user.getId());
    }
}