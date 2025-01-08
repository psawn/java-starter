package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.demo.rest.InstructorRestController.getDetailById(int))")
    public void logBeforeGetDetailById() {
        System.out.println("\n=====>>> Executing @Before advice on getDetailById()");
    }
}
