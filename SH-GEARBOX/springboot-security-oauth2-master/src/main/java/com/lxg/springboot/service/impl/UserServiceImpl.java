package com.lxg.springboot.service.impl;

import com.lxg.springboot.entity.Users;
import com.lxg.springboot.mapper.UserRepository;
import com.lxg.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

}
