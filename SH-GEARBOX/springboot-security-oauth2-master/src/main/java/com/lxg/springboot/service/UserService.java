package com.lxg.springboot.service;

import com.lxg.springboot.entity.Users;

import java.util.List;

/**
 * Created by lxg
 * on 2017/2/21.
 */
public interface UserService {

    Users findByUsername(String name);

    List<Users> findAll();

}
