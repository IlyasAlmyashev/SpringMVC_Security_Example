package jm.security.example.service;

import jm.security.example.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    void updateUser(User user);

    void removeUserById(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByName(String username);
}