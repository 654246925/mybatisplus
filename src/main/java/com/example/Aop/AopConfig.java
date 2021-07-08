package com.example.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


//@Aspect
//@Configuration
///**
// * AOP的基本概念
// * Advice(通知、切面)： 某个连接点所采用的处理逻辑，也就是向连接点注入的代码， AOP在特定的切入点上执行的增强处理。
// * @Before： 标识一个前置增强方法，相当于BeforeAdvice的功能.
// * @After： final增强，不管是抛出异常或者正常退出都会执行.
// * @AfterReturning： 后置增强，似于AfterReturningAdvice, 方法正常退出时执行.
// * @AfterThrowing： 异常抛出增强，相当于ThrowsAdvice.
// * @Around： 环绕增强，相当于MethodInterceptor.
// */
public class AopConfig {
//    private static Logger logger = LoggerFactory.getLogger(AopConfig.class);
//
//    /**
//     * 切入点
//     */
//    @Pointcut("execution(* com.example.controller.pub.*.*(..)) ")
//    public void executionService() {}
//
//    /**
//     * 方法调用之前调用
//     *
//     * @param joinPoint
//     */
//
//    @Before(value = "executionService()")
//    public void doBefore(JoinPoint joinPoint) {
//        logger.info("方法开始调用前");
//    }
//    /**
//     * 方法之后调用
//     *
//     * @param joinPoint
//     * @param returnValue 方法返回值
//     */
//
//    @AfterReturning(pointcut = "executionService()", returning = "returnValue")
//    public void doAfterReturning(JoinPoint joinPoint, Object returnValue) {
//        logger.info("方法开始调用之后");
//    }
//
//    /**
//     * 统计方法执行耗时Around环绕通知
//     *
//     * @param joinPoint
//     * @return
//     */
//
//    @Around("executionService()")
//    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable{
//        Object obj = null;
//        obj = joinPoint.proceed();
//        logger.info("Around");
//        return obj;
//    }


}