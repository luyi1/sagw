package com.lxg.springboot.test;

import com.lxg.springboot.Application;
import com.lxg.springboot.entity.Users;
import com.lxg.springboot.entity.UserRole;
import com.lxg.springboot.mapper.UserRepository;
import com.lxg.springboot.mapper.UserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
public class ApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void test() throws Exception {
        Users user = userRepository.findByUsername("admin");
        //List<UserRole> userRole = userRoleRepository.findByuid(1);


//        List<String> list = userRoleRepository.findRoleName(1);
        System.out.println(user);
    }
}
