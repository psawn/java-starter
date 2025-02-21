package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Order(2)
@Aspect
@Component
public class ControllerLoggingAspect {
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.example.demo.controller.*.* (..))")
    private void forControllerPackage() {
        System.out.println("go there");
    }

    @Pointcut("forControllerPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>>> in @Before: calling method: " + method);

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            logger.info("=====>>> argument: " + arg);
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        logger.info("=====>>> in @AfterReturning: from method: " + method);

        logger.info("=====>>> result: " + result);
    }
}

