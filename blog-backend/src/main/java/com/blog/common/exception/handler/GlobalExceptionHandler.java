package com.blog.common.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.common.domain.Result;
import com.blog.common.exception.BusinessException;
import com.blog.common.exception.InvalidCredentialsException;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * 
     * @param request HttpServletRequest
     * @param ex BusinessException
     * @return com.blog.common.domain.Result<java.lang.Void>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public Result<Void> exceptionHandler(HttpServletRequest request, BusinessException ex) {
        log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a business error", request.getMethod(), request.getRequestURI(), ex);
        return Result.fail(ex.getMessage());
    }

    /**
     * 用户未登录或登录信息失效异常
     * 
     * @param request HttpServletRequest
     * @param ex NotLoginException
     * @return com.blog.common.domain.Result<java.lang.Void>
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public Result<Void> exceptionHandler(HttpServletRequest request, NotLoginException ex) {
        log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a not login error", request.getMethod(), request.getRequestURI(), ex);
        return Result.fail("当前用户未登录或登录信息已失效");
    }

    /**
     * 认证失败异常
     * 
     * @param request HttpServletRequest
     * @param ex InvalidCredentialsException
     * @return com.blog.common.domain.Result<java.lang.Void>
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentialsException.class)
    public Result<Void> exceptionHandler(HttpServletRequest request, InvalidCredentialsException ex) {
        log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a invalid credentials error", request.getMethod(),
            request.getRequestURI(), ex);
        return Result.fail("用户名或密码错误");
    }

    /**
     * 授权失败异常
     * 
     * @param request HttpServletRequest
     * @param ex NotPermissionException
     * @return com.blog.common.domain.Result<java.lang.Void>
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotPermissionException.class)
    public Result<Void> exceptionHandler(HttpServletRequest request, NotPermissionException ex) {
        log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a not permission error", request.getMethod(), request.getRequestURI(),
            ex);
        return Result.fail("没有权限访问");
    }

    /**
     * 未知错误
     * 
     * @param request HttpServletRequest
     * @param ex Exception
     * @return com.blog.common.domain.Result<java.lang.Void>
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(HttpServletRequest request, Exception ex) {
        log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a unknown error", request.getMethod(), request.getRequestURI(), ex);
        return Result.fail("服务器异常");
    }
}
