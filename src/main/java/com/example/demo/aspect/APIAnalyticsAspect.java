package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Aspect
@Component
public class APIAnalyticsAspect {

    @Before("execution(* com.example.demo.rest.InstructorRestController.get* (..))")
    public void beforeGetPrefixFunction() {
        System.out.println("\n=====>>> Executing APIAnalytics @Before advice on any get prefix function");
    }
}
