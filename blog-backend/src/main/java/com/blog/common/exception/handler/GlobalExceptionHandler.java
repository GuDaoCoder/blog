package com.blog.common.exception.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.hutool.core.collection.CollUtil;
import com.blog.common.domain.Result;
import com.blog.common.exception.BusinessException;
import com.blog.common.exception.DataNotFoundException;
import com.blog.common.exception.InvalidCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author zouzhangpeng
 * @desc 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 参数校验异常
	 * @param request HttpServletRequest
	 * @param ex MethodArgumentNotValidException
	 * @return com.blog.common.domain.Result<java.lang.Void>
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public Result<Void> exceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
		String exceptionStr = Optional.ofNullable(ex)
			.map(MethodArgumentNotValidException::getBindingResult)
			.map(BindingResult::getFieldErrors)
			.map(CollUtil::getFirst)
			.map(FieldError::getDefaultMessage)
			.orElse(null);
		log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a argument not valid error", request.getMethod(),
				request.getRequestURL(), ex);
		return Result.fail(exceptionStr);
	}

	/**
	 * 参数转换错误异常
	 * @param request HttpServletRequest
	 * @param ex MethodArgumentTypeMismatchException
	 * @return com.blog.common.domain.Result<java.lang.Void>
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public Result<Void> exceptionHandler(HttpServletRequest request, HttpMessageNotReadableException ex) {
		log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a param convert error", request.getMethod(), request.getRequestURL(),
				ex);
		return Result.fail("参数转换错误");
	}

	/**
	 * 业务异常
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

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(DataNotFoundException.class)
	public Result<Void> exceptionHandler(HttpServletRequest request, DataNotFoundException ex) {
		log.warn(">>>>>>>>>>>>[{}]-[{}] occurred a data not found error", request.getMethod(), request.getRequestURI(),
				ex);
		return Result.fail("数据不存在或已被删除");
	}

	/**
	 * 未知错误
	 * @param request HttpServletRequest
	 * @param ex Exception
	 * @return com.blog.common.domain.Result<java.lang.Void>
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result<Void> exceptionHandler(HttpServletRequest request, Exception ex) {
		log.error(">>>>>>>>>>>>[{}]-[{}] occurred a unknown error", request.getMethod(), request.getRequestURI(), ex);
		return Result.fail("服务器异常");
	}

}
