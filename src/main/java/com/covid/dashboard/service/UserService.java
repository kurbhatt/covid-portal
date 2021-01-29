package com.covid.dashboard.service;

import com.covid.dashboard.data.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
