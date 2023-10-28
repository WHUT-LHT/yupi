package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 * author lht
 */

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    /**
     * 测试更新用户
     */
    @Test
    public  void testAddUser(){
        User user =new User();

        user.setUsername("dogYupi");
        user.setUserAccount("123");
        user.setAvatarUrl("\"D:\\桌面\\Jay\\IMG_5199.PNG\"");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setEmail("456");
        user.setPhone("123");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    /**
     * 测试用户注册
     */
    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "dogYupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result );

    }
}