package com.exlibris.exbliris.DTO;

import com.exlibris.exbliris.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class UserDTO {

    private List<User> userList = new ArrayList<>();

    public ResponseEntity<Object> addUser(User user) {
        User createdUser = user;
        userList.add(createdUser);

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    public ResponseEntity<Object> getUser(Long id) {
        User user = userList.get(Math.toIntExact(id));

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllUsers() {
        List<User> getUserList = userList
                .stream()
                .collect(Collectors.toList());

        return new ResponseEntity<>(getUserList, HttpStatus.OK);
    }

    public ResponseEntity<Object> editUser(User user) {
        User editedUser = user;

        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    public void deleteUser(Long id) {
        Predicate<User> userToDelete = user -> user.getId() == id;
        userList.removeIf(userToDelete);
    }
}