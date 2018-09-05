package com.lxg.springboot.service;

import com.lxg.springboot.entity.Users;

import java.util.List;

public interface UserService {

    Users findByUsername(String name);

    List<Users> findAll();

}
