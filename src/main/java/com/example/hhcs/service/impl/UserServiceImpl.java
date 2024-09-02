package com.example.hhcs.service.impl;

import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.User;
import com.example.hhcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User get(String id) {
        return userDao.get(id);
    }

    @Override
    public int adduser(User user) {
        return userDao.adduser(user);
    }
}
