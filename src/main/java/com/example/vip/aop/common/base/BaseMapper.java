package com.example.vip.aop.common.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * TK.mybatis公共基础类
 * @author Zyred
 * @param <T> 实体类型
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}