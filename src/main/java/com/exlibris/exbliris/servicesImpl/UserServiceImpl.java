package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.DAO.UserDAO;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) {
            return userDAO.addUser(user);
    }

    @Override
    public UserResponse getUser(Long id) {
            return userDAO.getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
            return userDAO.getAllUsers();
    }

    @Override
    public void editUser(Long id, User user) {
            ResponseEntity<Object> getUser = userDAO.getUser(id);

            User userToEdit = (User) getUser.getBody();

            userToEdit.setUsername(user.getUsername());
            userToEdit.setEmail(user.getEmail());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());

            return userDAO.addUser(userToEdit);
    }

    @Override
    public void deleteUser(Long id) {
            return userDAO.deleteUser(id);
    }
}
