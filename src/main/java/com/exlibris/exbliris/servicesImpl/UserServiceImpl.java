package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.DB.UserDB;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    private final UserDB userDB;

    @Autowired
    public UserServiceImpl(UserDB userDB) {
        this.userDB = userDB;
    }

    @Override
    public ResponseEntity<Object> addUser(User user) {
        try {
            ResponseEntity<Object> checkIfUserExists = userDB.getUser(user.getId());
            if (checkIfUserExists != null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return userDB.addUser(user);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Object> getUser(Long id) {
        try {
            return userDB.getUser(id);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        try {
            return userDB.getAllUsers();
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Object> editUser(Long id, User user) {
        try {
            ResponseEntity<Object> getUser = userDB.getUser(id);

            User userToEdit = (User) getUser.getBody();

            userToEdit.setUsername(user.getUsername());
            userToEdit.setEmail(user.getEmail());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());

            userDB.addUser(userToEdit);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            return userDB.deleteUser(id);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }
}
