package com.taotao.manage.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author lyz
 * @Title: TimerLogInterceptor
 * @Description:
 * @date 2018/8/316:36
 */
@Aspect
@Component
public class LogInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    @Pointcut("execution(* com.taotao.manage.service..*(..))")
    private void myAspectJMethod(){}

    //@Around("myAspectJMethod()")
    public Object doArround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long beforeTime = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long afterTime = System.currentTimeMillis();
        long times = afterTime - beforeTime;
        if (logger.isInfoEnabled()) {
        	logger.info("统一日志打印:"+ "方法执行时间："+times);
		}
        return proceed;
    }

    @AfterReturning(value = "myAspectJMethod()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal){
    	if (logger.isInfoEnabled()) {
    		logger.info("统一日志打印:"+ joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName()+"返回值为:"+ returnVal);
		}
    }

    @Before("myAspectJMethod()")
    public void logArgs(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        String argsStr = "";
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0 || parameterNames == null || parameterNames.length == 0) {
			return;
		}
        for (int i = 0; i < args.length;i++){
            argsStr = argsStr + "," + parameterNames[i] + "=" + args[i];
        }
        if (logger.isInfoEnabled()) {
        	logger.info("统一日志打印:"+ joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName() + "开始执行" + "参数" + argsStr);
		}
    }
}
