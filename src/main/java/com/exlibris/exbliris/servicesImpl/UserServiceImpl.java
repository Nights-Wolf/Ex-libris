package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.DB.UserDB;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDB userDB;

    @Autowired
    public UserServiceImpl(UserDB userDB) {
        this.userDB = userDB;
    }

    @Override
    public ResponseEntity<Object> addUser(User user) {
        return userDB.addUser(user);
    }

    @Override
    public ResponseEntity<Object> getUser(Long id) {
        return userDB.getUser(id);
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        return userDB.getAllUsers();
    }

    @Override
    public ResponseEntity<Object> editUser(Long id, User user) {
        ResponseEntity<Object> getUser= userDB.getUser(id);
        User userToEdit = (User) getUser.getBody();

        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setName(user.getName());
        userToEdit.setSurname(user.getSurname());

        userDB.addUser(userToEdit);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteUser(Long id) {
        return userDB.deleteUser(id);
    }
}
