package com.lxg.springboot.service;

import java.util.List;

public interface UserRoleService {

    List<String> findRoles(int uid);
}
