package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User index(long id);

    void createUser(User user);

    void updateUser(long id, User updatedUser);

    void deleteUser(long id);

    User findById(long id);

}