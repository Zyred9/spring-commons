package com.example.vip.aop.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.example.vip.aop.common.enum1.ResponseEnum;
import com.example.vip.aop.controllers.commonservice.CommonService;
import com.example.vip.aop.user.entity.User;
import com.example.vip.aop.user.mapper.UserMapper;
import com.example.vip.aop.user.service.UserService;
import com.example.vip.aop.utils.*;
import com.google.zxing.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用戶服务层
 *
 * @Author: Zyred
 * @Date: 2020/2/26 20:07
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil util;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Response<String> login(User user, HttpServletRequest req, HttpServletResponse resp) {
        if (CalibrationUtils.isNull(user.getUsername(), user.getPassword())) {
            user.setPassword(DigestUtils.sha1DigestAsHex(user.getPassword()).toUpperCase());
            Example ex = new Example(User.class);
            ex.createCriteria().andEqualTo("username", user.getUsername());
            User dbUser = userMapper.selectOneByExample(ex);
            if (Objects.isNull(dbUser)) {
                return Response.error(ResponseEnum.EMPTY_USER.getCode(), ResponseEnum.EMPTY_USER.getMsg());
            }
            if (user.getPassword().equals(dbUser.getPassword())) {
                String token = Jwt.generatorToken(JSON.parseObject(user.toString(), Map.class));
                String key = Constant.REDIS_SESSION_PREFIX.concat(token);
                util.set(key, dbUser.toString());
                CookieUtils.setCookie(req, resp, Constant.ACCESS_TOKEN, token);
                return Response.ok("Success", Constant.EMPTY);
            }
            return Response.error(ResponseEnum.INFO_ERROR.getCode(), ResponseEnum.INFO_ERROR.getMsg());
        }
        return Response.error(ResponseEnum.EMPTY_CONTENT.getCode(), ResponseEnum.EMPTY_CONTENT.getMsg());
    }


    @Override
    public Response<String> registry(User user) {
        String key = Constant.REGISTRY_EMAIL_CODE_REDIS_KEY.concat(user.getQqEmail());
        String registryCode = String.valueOf(util.get(key));
        String code = registryCode == null ? "" : registryCode;
        if(!code.equals(user.getRegistryCode())){
            return Response.error(ResponseEnum.CODE_ERROR.getCode(), ResponseEnum.CODE_ERROR.getMsg());
        }
        // 加密密码
        user.setPassword(DigestUtils.sha1DigestAsHex(user.getPassword()));
        //验证请求参数是否完整
        if (CalibrationUtils.isNull(user.getPassword(), user.getUsername())) {
            return Response.error(ResponseEnum.EMPTY_CONTENT.getCode(), ResponseEnum.EMPTY_CONTENT.getMsg());
        }
        // 校验数据是否已经存在
        List<User> isa = userMapper.select(user);
        if (CollectionUtil.isNotEmpty(isa)) {
            return Response.error(ResponseEnum.THE_USER_ALREADY_EXISTS.getCode(),
                    ResponseEnum.THE_USER_ALREADY_EXISTS.getMsg());
        }
        userMapper.insertSelective(user);
        util.del(key);
        return Response.ok("Success");
    }


    @Override
    public List<User> getUsers() {
        return userMapper.findListUser();
    }


    @Override
    public Response<String> updateUser(User user) {
        userMapper.updateByPrimaryKey(user);
        return Response.ok(ResponseEnum.OK_SUCCESS.getCode(), ResponseEnum.OK_SUCCESS.getMsg());
    }


    @Override
    public void createUser() {
        List<User> names = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername(RandomUtils.getRandomName(3));
            user.setPassword(DigestUtils.sha1DigestAsHex(RandomUtils.getStringRandom(20)));
            user.setQqEmail(RandomUtils.getTenRandom()+"@qq.com");
            names.add(user);
        }
        userMapper.insertList(names);
    }


}
