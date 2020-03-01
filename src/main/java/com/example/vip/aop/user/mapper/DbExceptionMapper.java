package com.example.vip.aop.user.mapper;

import com.example.vip.aop.user.entity.DbException;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Zyred
 * @Date: 2020/2/27 22:57
 */
@Mapper
public interface DbExceptionMapper {

    /**
     * 插入异常信息
     * @param mapper
     */
    @Insert(" insert into exception (" +
            " time, class_name, method_name, parameters, exception_msg, " +
            " parameter_type, request_path, exception_file, exception_line) " +
            " values(#{time}, #{className}, #{methodName}, #{parameters}, #{exceptionMsg}, " +
            "#{parameterType}, #{requestPath}, #{exceptionFile}, #{exceptionLine})")
    void insertException(DbException mapper);

}
