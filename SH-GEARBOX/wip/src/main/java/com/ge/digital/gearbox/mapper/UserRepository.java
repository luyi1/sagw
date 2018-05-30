package com.ge.digital.gearbox.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ge.digital.gearbox.entity.Users;

/**
 * Created by lxg
 * on 2017/2/20.
 */
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String name);

}
