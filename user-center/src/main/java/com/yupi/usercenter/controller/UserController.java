package com.yupi.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author LHT
 */
@RestController //打上这个注解之后，类里面所有的请求的接口相应的数据的类型都是application/json,告诉服务端消息主体是序列化的JSON字符串
@RequestMapping("/user")//定义请求的路径

public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        //注解的作用是让SpringMVC框架把前端传来的json参数和对象做关联
        if(userRegisterRequest == null){
            return null;
        }
        //
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }

        return userService.userRegister(userAccount,userPassword,checkPassword);
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest , HttpServletRequest request){
        //注解的作用是让SpringMVC框架把前端传来的json参数和对象做关联
        if(userLoginRequest == null){
            return null;
        }
        //
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }

        return userService.userLogin(userAccount,userPassword,request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request){

        if(!isAdmin(request)){
            return  new ArrayList<>();
        }
        //仅管理员可查询

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }

        //查询符合条件的用户列表，并将结果存储在userList中
        List<User> userList  = userService.list(queryWrapper);
        //使用map函数将每个用户对象的密码设为null，
        // 然后通过collect函数将处理后的用户对象收集到一个新的列表中。
        return userList.stream().map(user ->{
            user.setUserPassword(null);
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
    }


    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id, HttpServletRequest request){

        if(!isAdmin(request)){
            return  false;
        }
        if (id <= 0){
            return false;
        }

        return userService.removeById(id);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if(user == null || user.getUserRole() != ADMIN_ROLE){
            return false;
        }
        return true;
    }

}
