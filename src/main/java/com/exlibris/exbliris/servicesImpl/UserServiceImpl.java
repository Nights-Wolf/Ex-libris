package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
          userDAO.addUser(user);
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
            UserResponse getUser = userDAO.getUser(id);

            UserResponse userToEdit = getUser;

            userToEdit.setUsername(user.getUsername());
            userToEdit.setEmail(user.getEmail());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());

            userDAO.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
            userDAO.deleteUser(id);
    }
}
