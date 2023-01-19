package com.exlibris.exbliris.api;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getUser(@PathVariable("id") Long id) {
        try {
            UserResponse userResponse = userService.getUser(id);
            if (userResponse == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAllUsers() {
        try {
            List<User> userList = userService.getAllUsers();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> editUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            userService.editUser(id, user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
