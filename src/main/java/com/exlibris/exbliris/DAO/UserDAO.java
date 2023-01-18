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

    public void addUser(User user) {
        userList.add(user);
    }

    public UserResponse getUser(Long id) {
        List<User> userFromList = userList.stream()
                .filter(users -> Objects.equals(users.getId(), id))
                .toList();
        try {
            User user = userFromList.get(0);
            UserResponse userResponse = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getName(), user.getSurname());
            return userResponse;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
            List<User> getUserList = new ArrayList<>(userList);

            return getUserList;
    }

    public void editUser(User user) {
        List<User> userFromList = userList.stream()
                .filter(users -> users.getId() == user.getId())
                .toList();

        User editedUser = userFromList.get(0);
        Predicate<User> userToDelete = users -> users.getId() == user.getId();
        userList.removeIf(userToDelete);

        userList.add(editedUser);
    }

    public void deleteUser(Long id) {
        Predicate<User> userToDelete = user -> user.getId() == id;
        userList.removeIf(userToDelete);
    }
}
