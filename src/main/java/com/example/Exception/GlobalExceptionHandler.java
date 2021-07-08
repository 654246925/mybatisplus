package com.example.Exception;

import com.alibaba.fastjson.JSON;
import com.example.result.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 統一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理运行异常
	 */
	@ExceptionHandler(RuntimeException.class)
	public Result handleRuntimeException(HttpServletRequest request,
										 RuntimeException ex, BusinessException e) {
		Result result = null;
		// 自己抛出的异常还是其它异常
		if (ex instanceof BusinessException) {
			log.error("抛出自定义异常:[{}],请求地址：[{}],请求参数：[{}]", e.getMessage(),
					request.getRequestURL(), JSON.toJSONString(request
							.getParameterMap()));
			result = Result.genErrorResult(e.getMessage());
		} else {
			log.error(ex.getMessage(), ex);
			log.error("请求地址：[{}]", request.getRequestURL());
			log.error("请求参数:[{}]", JSON.toJSONString(request
					.getParameterMap()));
			result = Result.genErrorResult(ex.getMessage());
		}
		return result;
	}

	/**
	 * 
	 * 处理其他异常 用来捕获404,400 这种无法到达controller的错误,似乎无效 404
	 * 被tomcat容器拦截了到不了controller,也就到不了@ControllerAdvice
	 * 
	 * @param ex
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	public Result defaultErrorHandler(HttpServletRequest request,
			Exception ex) throws Exception {
		log.error(ex.getMessage(), ex);
		log.error("请求地址：[{}]", request.getRequestURL());
		log.error("请求参数:[{}]", JSON.toJSONString(request.getParameterMap()));
		Result result;
		if (ex instanceof ServletRequestBindingException) {
			result = Result.genErrorResult("缺少必要的参数");
		} else if (ex instanceof HttpRequestMethodNotSupportedException) {
			result = Result.genErrorResult("请求方式不支持");
		}else if (ex instanceof MissingServletRequestParameterException) {
			result = Result.genErrorResult("请求参数缺失");
		}else {
			result = Result.genErrorResult(ex.getMessage());
		}
		return result;
	}
}