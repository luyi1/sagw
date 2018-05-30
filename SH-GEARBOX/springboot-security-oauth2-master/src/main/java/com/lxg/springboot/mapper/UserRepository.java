package com.lxg.springboot.mapper;

import com.lxg.springboot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lxg
 * on 2017/2/20.
 */
@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String name);

}
