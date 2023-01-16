package com.exlibris.exbliris.DB;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class UserDB {

    private List<User> userList = new ArrayList<>();

    public ResponseEntity<Object> addUser(User user) {
        User createdUser = user;
        userList.add(createdUser);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getUser(Long id) {
        User user = userList.get(Math.toIntExact(id));
        UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getSurname());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllUsers() {
        List<User> getUserList = userList
                .stream()
                .collect(Collectors.toList());

        return new ResponseEntity<>(getUserList, HttpStatus.OK);
    }

    public ResponseEntity<Object> editUser(User user) {
        User editedUser = user;

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        Predicate<User> userToDelete = user -> user.getId() == id;
        userList.removeIf(userToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
