package com.example.hhcs.service;

import com.example.hhcs.domain.User;

public interface UserService {
    User get(String id);
    int adduser(User user);
}
