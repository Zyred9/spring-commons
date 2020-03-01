package com.example.vip.aop.user.service;

import com.example.vip.aop.user.entity.User;
import com.example.vip.aop.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Zyred
 */
public interface UserService {

    /**
     * 根据用户名查询用户
     * @param user
     * @param req
     * @param resp
     * @return
     */
    Response<String> login(User user, HttpServletRequest req, HttpServletResponse resp);

    /**
     * 用户注册
     * @param user
     * @return
     */
    Response<String> registry(User user);

    /**
     * 获取用户集合
     * @return
     */
    List<User> getUsers();

    /**
     * 更新用户
     * @param user
     * @return
     */
    Response<String> updateUser(User user);

    /**
     * 随机创建用户对象
     */
    void createUser();

}
