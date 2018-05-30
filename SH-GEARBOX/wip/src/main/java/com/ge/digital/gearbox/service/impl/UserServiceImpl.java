package com.ge.digital.gearbox.service.impl;

import com.ge.digital.gearbox.entity.Users;
import com.ge.digital.gearbox.mapper.UserRepository;
import com.ge.digital.gearbox.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lxg
 * on 2017/2/21.
 */
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
