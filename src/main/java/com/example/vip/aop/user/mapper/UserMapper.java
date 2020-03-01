package com.example.vip.aop.user.mapper;

import com.example.vip.aop.user.entity.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据层
 * @author Zyred
 */
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {

    /**
     * 查询用户集合
     * @return
     */
    @Select("select id, username, password, qq_email from user")
    List<User> findListUser();

    /**
     * 随机查询数据
     * @return
     */
    @Select("SELECT qq_email FROM `user` WHERE id >= ( SELECT floor( RAND( ) * ( SELECT MAX( id ) FROM `user` ) ) )  ORDER BY id  LIMIT 10;")
    ArrayList<String> getRandomEmail();

}
