package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.user.Users;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(Users users) {
          userRepository.save(users);
    }

    @Override
    public UserResponse getUser(Long id) {
            Optional<Users> user = userRepository.findById(id);

            if (user.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            UserResponse response = new UserResponse(user.get().getId(), user.get().getUsername(), user.get().getEmail(),
                    user.get().getName(), user.get().getSurname());

            return response;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void editUser(Long id, Users users) {
            Optional<Users> userToEdit = userRepository.findById(id).map(editingUser -> users);

            if (userToEdit.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            userRepository.save(userToEdit.get());
    }

    @Override
    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }
}
