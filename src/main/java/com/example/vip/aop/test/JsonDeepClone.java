package com.example.vip.aop.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON实现对象的深克隆
 *
 * @Author: Zyred
 * @Date: 2020/3/1 9:58
 */
public class JsonDeepClone {

    public static void main(String[] args) {
        // 得到对象实例
        User newUser = new User();
        newUser.setAge("20");
        newUser.setName("张三");
        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            lists.add(i + "h-");
        }
        newUser.setLists(lists);

        //深克隆对象
        User cloneUser = deepClone(newUser);
        List<String> cloneList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cloneList.add(i + "h+");
        }
        cloneUser.setLists(cloneList);
        System.out.println("newUser:" + newUser);
        System.out.println("cloneUser:" + cloneUser);
        System.out.println(newUser == cloneUser);
    }

    /** 利用JSON实现深克隆 **/
    public static User deepClone(User newUser){
        return JSON.parseObject(JSON.toJSONString(newUser), User.class);
    }
}

/**
 * User对象
 */
@Data
class User{
    private String name;
    private String age;
    private List<String> lists;
}