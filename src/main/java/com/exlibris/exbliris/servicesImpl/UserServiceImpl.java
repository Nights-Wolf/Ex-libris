package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.DAO.UserDAO;
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

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public ResponseEntity<Object> addUser(User user) {
        try {
            return userDAO.addUser(user);
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
            return userDAO.getUser(id);
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
            return userDAO.getAllUsers();
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
            ResponseEntity<Object> getUser = userDAO.getUser(id);

            User userToEdit = (User) getUser.getBody();

            userToEdit.setUsername(user.getUsername());
            userToEdit.setEmail(user.getEmail());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());

            userDAO.addUser(userToEdit);

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
            return userDAO.deleteUser(id);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }
}
