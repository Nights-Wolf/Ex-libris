package com.exlibris.exbliris.api;

import com.exlibris.exbliris.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;

@RestController
@RequestMapping("api/user")
public class UserRestController {

    @Autowired
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    private ResponseEntity<Object> addUser(User user) {
        try {
            return userService.addUser(user);
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
            return userService.getUser(id);
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
            return userService.getAllUsers();
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> editUser(@PathVariable("id") Long id) {
        try {
            return userService.editUser(id);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    private void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser();
    }
}
