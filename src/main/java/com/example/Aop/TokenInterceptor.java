package com.example.Aop;

import com.example.result.Result;
import com.example.result.ResultCode;
import com.example.tool.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面检测token
 * 
 * @author anxuan
 */
@Aspect
@Order(1)
// 设置切面的执行顺序,order 的值越小,说明越先被执行
@Component
@Slf4j
public class TokenInterceptor {

	/**
	 * 切入点
	 */
	@Pointcut("execution(* com.example.controller.auth.*.*(..)))")
	public void executionService() {}

	/**
	 * 统计方法执行耗时Around环绕通知
	 *
	 * @param joinPoint
	 * @return
	 */

	@Around("executionService() && @annotation(tokenAnnotation)")
	public Object timeAround(ProceedingJoinPoint joinPoint, TokenAnnotation tokenAnnotation) throws Throwable{
		Object obj = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		// 在请求头中获取token
		String token = request.getHeader("token");
		if (StringHelper.isEmpty(token)) {
			token = request.getParameter("token");
		}
		if (StringHelper.isEmpty(token)) {
			return Result.genErrorResult(ResultCode.ERROR_TOKEN);
		}
		obj = joinPoint.proceed();
		return obj;
	}

}
