package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.models.user.UserResponse;
import com.exlibris.exbliris.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addUser(User user) {
          userRepository.save(user);
    }

    @Override
    public UserResponse getUser(Long id) {
            Optional<User> user = userRepository.findById(id);

            UserResponse response = new UserResponse(user.get().getId(), user.get().getUsername(), user.get().getEmail(),
                    user.get().getName(), user.get().getSurname());

            return response;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void editUser(Long id, User user) {
            Optional<User> userToEdit = userRepository.findById(id);

            userToEdit.stream()
                            .map(editingUser -> user);

            userRepository.save(userToEdit.get());
    }

    @Override
    public void deleteUser(Long id) {
            userRepository.deleteById(id);
    }
}
