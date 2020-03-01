package com.example.vip.aop.common.runner;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.example.vip.aop.user.entity.User;
import com.example.vip.aop.user.mapper.UserMapper;
import com.example.vip.aop.utils.Constant;
import com.example.vip.aop.utils.ListUtils;
import com.example.vip.aop.utils.RedisUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务启动加载用户数据到Redis中
 *
 * @Author Zyred
 * @Date: 2020/2/27 12:03
 */
@Slf4j
@Component
public class ServiceRunnerLoadForRedis implements CommandLineRunner {


    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(String... args) throws Exception {
        // 查询用户
        List<User> usersForDb = userMapper.findListUser();
        // 初始化线程池
        log.info("添加用户 等待服务启动完成....");
        // 得到相同大小的List<List<User>>
        List<List<User>> lists = ListUtils.averageAssign(usersForDb, 7);
        // 遍历外层集合
        for (int i = 0, len = lists.size(); i < len; i++) {
            List<User> users = lists.get(i);
            // 开启线程池进行提交数据, Hutool工具类提供公共线程池
            ThreadUtil.execute(() -> {
                log.info("创建提交线程：" + Thread.currentThread().getName());
                for (int j = 0; j < users.size(); j++) {
                    User user = users.get(j);
                    String key = Constant.USER_INFO_REDIS_PREFIX.concat(String.valueOf(user.getId()));
                    if (!redisUtil.hasKey(key)) {
                        String value = JSON.toJSONString(user);
                        log.info("数据添加中：" + value);
                        redisUtil.set(key, value, 24 * 60 * 60);
                    }
                }
            });
        }
        log.info("添加用户完毕，服务启动完毕");
    }
}
