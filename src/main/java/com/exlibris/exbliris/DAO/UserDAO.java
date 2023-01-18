package com.exlibris.exbliris.DAO;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Repository
public class UserDAO {

    private List<User> userList = new ArrayList<>();

    public ResponseEntity<Object> addUser(User user) {
        userList.add(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Object> getUser(Long id) {
        List<User> userFromList = userList.stream()
                .filter(users -> Objects.equals(users.getId(), id))
                .toList();
        try {
            User user = userFromList.get(0);
            UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getSurname());
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> getAllUsers() {
            List<User> getUserList = new ArrayList<>(userList);

            return new ResponseEntity<>(getUserList, HttpStatus.OK);
    }

    public ResponseEntity<Object> editUser(User user) {
        List<User> userFromList = userList.stream()
                .filter(users -> users.getId() == user.getId())
                .toList();

        User editedUser = userFromList.get(0);
        Predicate<User> userToDelete = users -> users.getId() == user.getId();
        userList.removeIf(userToDelete);

        userList.add(editedUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> deleteUser(Long id) {
        Predicate<User> userToDelete = user -> user.getId() == id;
        userList.removeIf(userToDelete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
