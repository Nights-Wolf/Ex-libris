package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.user.Users;
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
class UsersServiceImplUnitTest {

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private Users users = new Users(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private UserResponse userResponse = new UserResponse(1L, "NightsWolf", "dawi@wp.pl", "Dawid", "Całkowksi");

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldAddUser() {
        userService.addUser(users);
        ArgumentCaptor<Users> userArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        Users response = userArgumentCaptor.getValue();

        Assertions.assertEquals(users, response);
    }

    @Test
    void shouldGetUser() {
        Mockito.when(userRepository.findById(users.getId())).thenReturn(Optional.ofNullable(users));
        UserResponse response = userService.getUser(users.getId());

        Assertions.assertEquals(userResponse.getId(), response.getId());
    }

    @Test
    void shouldThrowUserNotFound() {
        Mockito.when(userRepository.findById(users.getId())).thenThrow(HttpClientErrorException.class);
        Assertions.assertThrows(HttpClientErrorException.class, () -> userService.getUser(users.getId()));
    }

    @Test
    void shouldGetAllUsers() {
        List<Users> usersList = new ArrayList<>();
        usersList.add(users);

        Mockito.when(userRepository.findAll()).thenReturn(usersList);
        List<Users> response = userService.getAllUsers();

        Assertions.assertEquals(usersList, response);
    }

    @Test
    void shouldEditUser() {
        Users editedUsers = new Users(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
        Mockito.when(userRepository.findById(users.getId())).thenReturn(Optional.ofNullable(users));

        userService.editUser(users.getId(), editedUsers);
        ArgumentCaptor<Users> userArgumentCaptor = ArgumentCaptor.forClass(Users.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        Users response = userArgumentCaptor.getValue();

        Assertions.assertEquals(editedUsers, response);
    }

    @Test
    void shouldThrowUserToEditNotFound() {
        Users editedUsers = new Users(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
        Mockito.when(userRepository.findById(users.getId())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> userService.editUser(users.getId(), editedUsers));
    }

    @Test
    void deleteUser() {
        userService.deleteUser(users.getId());
        Mockito.verify(userRepository).deleteById(users.getId());
    }
}