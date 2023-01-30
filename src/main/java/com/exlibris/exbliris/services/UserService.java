package com.exlibris.exbliris.services;

import com.exlibris.exbliris.models.user.Users;
import com.exlibris.exbliris.models.user.UserResponse;

import java.util.List;

public interface UserService {

    void addUser(Users users);
    UserResponse getUser(Long id);
    List<Users> getAllUsers();
    void editUser(Long id, Users users);
    void deleteUser(Long id);
}
