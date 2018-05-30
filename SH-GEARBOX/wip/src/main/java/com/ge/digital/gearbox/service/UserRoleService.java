package com.ge.digital.gearbox.service;

import java.util.List;

/**
 * Created by lxg
 * on 2017/2/21.
 */
public interface UserRoleService {

    List<String> findRoles(int uid);
}
