package com.zemoso.springboot.assignment.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.zemoso.springboot.assignment.controller.DoctorController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Calling method: {}.{}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.zemoso.springboot.assignment.controller.DoctorController.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Returning from method: {}.{}. Return value: {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), result);
    }
}
