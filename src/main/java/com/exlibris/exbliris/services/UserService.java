package com.exlibris.exbliris.services;

import com.exlibris.exbliris.models.user.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<Object> addUser(User user);
    ResponseEntity<Object> getUser(Long id);
    ResponseEntity<Object> getAllUsers();
    ResponseEntity<Object> editUser(Long id, User user);
    ResponseEntity<Object> deleteUser(Long id);
}
