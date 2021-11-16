package by.vsu.service;

import by.vsu.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    void save(User user);
}
