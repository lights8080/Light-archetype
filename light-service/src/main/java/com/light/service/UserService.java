package com.light.service;

import com.light.model.User;

import java.util.List;

public interface UserService {
    Boolean add(User user);

    List<User> list();
}
