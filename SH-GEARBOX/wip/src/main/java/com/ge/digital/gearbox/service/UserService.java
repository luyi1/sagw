package com.ge.digital.gearbox.service;

import java.util.List;

import com.ge.digital.gearbox.entity.Users;

/**
 * Created by lxg
 * on 2017/2/21.
 */
public interface UserService {

    Users findByUsername(String name);

    List<Users> findAll();

}
