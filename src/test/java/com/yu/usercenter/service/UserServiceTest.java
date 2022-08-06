package com.yu.usercenter.service;

import com.yu.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    void addUser(){
    User user = new User();

    user.setUsername("yuPi");
    user.setColumn_4(0);
    user.setUserAccount("xxx");
    user.setUserPassword("xxx");
    user.setAvatarUrl("xxx");
    user.setPhone("xxx");
    user.setUserStatus(0);
    user.setIsDelete(0);
        boolean save = userService.save(user);
        Assertions.assertTrue(save);
        System.out.println(user.getId());

    }

    @Test
    void userRegister() {
    String userAccount ="jin";
    String username = "12345";
    String checkUsername ="12345";
        long l = userService.userRegister(userAccount, username, checkUsername);
        System.out.println(l);
    }
}