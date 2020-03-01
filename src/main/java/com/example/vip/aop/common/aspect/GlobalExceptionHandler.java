package com.example.vip.aop.common.aspect;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.example.vip.aop.common.enum1.ResponseEnum;
import com.example.vip.aop.common.exception.WithoutPermissionException;
import com.example.vip.aop.user.entity.DbException;
import com.example.vip.aop.user.mapper.DbExceptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * AOP全局异常处理类
 * 先执行 afterBodyRead 方法，执行完毕后再执行 exceptionHandler
 * 将 afterBodyRead 执行后得到的有用参数放入ThreadLocal进行保存，
 * exceptionHandler 拿到参数进行数据库日志保存。
 *
 * @author Zyred
 */
@Slf4j
@ControllerAdvice(basePackages = "com.example.vip.aop")
public class GlobalExceptionHandler implements RequestBodyAdvice {

    /** 注入Mapper，注意该处不能注入tk.mybatis的实例 **/
    @Resource
    private DbExceptionMapper dbExceptionMapper;

    /** 线程私有数据，避免高并发带来的异常获取错误 **/
    private ThreadLocal<DbException> threadLocal = new ThreadLocal<>();

    /**
     * 全局异常处理
     * 向前端输出内容 {"exception":"", "code":"", "msg":""}
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> exceptionHandler(Exception e) {
        // 日志到数据库
        loggerForDB(e);
        Map<String, Object> error = new HashMap<>(4);
        error.put("exception", e.getMessage());
        if (e instanceof WithoutPermissionException) {
            error.put("code", ResponseEnum.WITHOUT_PERMISSION.getCode());
            error.put("msg", ResponseEnum.WITHOUT_PERMISSION.getMsg());
        } else {
            error.put("code", ResponseEnum.NOT_LOGIN.getCode());
            error.put("msg", ResponseEnum.NOT_LOGIN.getMsg());
        }
        return error;
    }

    /**
     * 异常抛出之前执行该方法，获取到 HTTP 请求部分参数。
     * @param o  方法请求参数
     * @param httpInputMessage  HTTP 请求信息
     * @param methodParameter   异常方法
     * @param type              non
     * @param aClass            non
     * @return
     */
    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage,
                                MethodParameter methodParameter, Type type,
                                Class<? extends HttpMessageConverter<?>> aClass) {
        RequestMapping methodAnnotation = methodParameter.getMethodAnnotation(RequestMapping.class);
        RequestMapping clazzAnnotation = methodParameter.getDeclaringClass().getAnnotation(RequestMapping.class);
        String classPath = StringUtils.arrayToDelimitedString(clazzAnnotation.value(), ",");
        String methodPath = StringUtils.arrayToDelimitedString(methodAnnotation.value(), ",");
        DbException e = new DbException();
        e.setParameters(JSON.toJSONString(o));
        e.setParameterType(JSON.toJSONString(type));
        e.setRequestPath(httpInputMessage.getHeaders().getOrigin().concat(classPath).concat(methodPath));
        threadLocal.set(e);
        return o;
    }

    /**
     * 日志到数据库存储
     *
     * @param e
     */
    private void loggerForDB(Exception e) {
        DbException db = Objects.isNull(threadLocal.get()) ? new DbException() : threadLocal.get();
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        String className = stackTraceElement.getClassName();
        db.setTime(DateUtil.now());
        db.setClassName(className);
        db.setExceptionMsg(ExceptionUtil.getMessage(e));
        db.setExceptionFile(e.getStackTrace()[0].getFileName());
        db.setMethodName(e.getStackTrace()[0].getMethodName());
        db.setExceptionLine(String.valueOf(e.getStackTrace()[0].getLineNumber()));
        dbExceptionMapper.insertException(db);
        e.printStackTrace();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage,
                                           MethodParameter methodParameter, Type type,
                                           Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return httpInputMessage;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage,
                                  MethodParameter methodParameter, Type type,
                                  Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}
