package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author LHT
 * @description 针对表【user(用户)】的数据库操作Service，业务接口，只定义要做什么，但不去实现
 * @createDate 2023-10-20 21:54:51
 * 用户登录
 */
public interface UserService extends IService<User> {



    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword  用户密码
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
    //从请求的session中读取值，往请求的session中写入值

}
