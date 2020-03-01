package com.example.vip.aop.user.controller;

import com.example.vip.aop.common.annotation.Auth;
import com.example.vip.aop.common.enum1.AuthEnum;
import com.example.vip.aop.user.entity.User;
import com.example.vip.aop.user.service.UserService;
import com.example.vip.aop.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Zyred
 * @className UserLoginController
 * @Date 2019/9/6 下午2:52
 **/
@Api(tags = { "用户登录" })
@RestController
@RequestMapping("/user")
public class UserLoginController {


    @Autowired
    private UserService userService;


    @CrossOrigin
    @Auth(AuthEnum.LOGIN)
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Response<String> login(@RequestBody User user, HttpServletRequest req, HttpServletResponse resp){
        return userService.login(user, req, resp);
    }

    @Auth(AuthEnum.NON)
    @PostMapping("/registry")
    @ApiOperation("注册")
    public Response<String> registry(@RequestBody User user){
        // TODO 添加注册发送邮件
        return userService.registry(user);
    }

    @Auth(AuthEnum.UPDATE)
    @ApiOperation(value = "测试未登录拦截")
    @PostMapping("/updateUser")
    public Response<String> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @ApiOperation(value = "测试无权限拦截")
    @GetMapping("/getUsers")
    public List<User> getUsers(){
        return userService.getUsers();
    }


    @Auth(AuthEnum.CREATE)
    @ApiOperation(value = "批量添加用户")
    @PostMapping("/createUser")
    public void createUser(){
        userService.createUser();
    }
}
