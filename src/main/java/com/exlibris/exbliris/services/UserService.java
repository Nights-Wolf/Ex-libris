package com.exlibris.exbliris.services;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;

import java.util.List;

public interface UserService {

    User addUser(User user);
    UserResponse getUser(Long id);
    List<User> getAllUsers();
    void editUser(Long id, User user);
    void deleteUser(Long id);
}
