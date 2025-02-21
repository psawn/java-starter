package com.example.demo.aspect;

import com.example.demo.entity.Instructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

// lower number mean higher priority -> this aspect will run first
@Order(1)
@Aspect
@Component
public class LoggingAspect {
    // * getStudentByStudentId(int) mean match any getStudentByStudentId function from any class
    // param-pattern: () mean no param, (*) mean one param of any type, (..) mean many param of any type

    // @Before("execution(public com.example.demo.dto.StudentDTO getStudentByStudentId(int))")
    // @Before("execution(* getStudentByStudentId(int))")
    @Before("execution(* com.example.demo.rest.InstructorRestController.get* (..))")
    public void beforeGetPrefixFunction(JoinPoint joinPoint) {
        System.out.println("\n=====>>> Executing LoggingAspect @Before advice on any get prefix function");

        String methodSignature = joinPoint.getSignature().toString();

        System.out.println("Method: " + methodSignature);

        Object[] args = joinPoint.getArgs();

        System.out.println("Arguments: ");
        for (Object arg : args) {
            System.out.println(arg);
        }
    }

    @AfterReturning(
            pointcut = "execution(* com.example.demo.rest.InstructorRestController.get* (..))",
            returning = "result"
    )
    public void afterReturningGetPrefixFunction(JoinPoint joinPoint, Object result) {
        System.out.println("\n=====>>> Executing LoggingAspect @AfterReturning advice on any get prefix function");

        String methodSignature = joinPoint.getSignature().toString();

        System.out.println("Method: " + methodSignature);

        System.out.println("Result: " + result);

        // post process data return from method
        // this.convertNameToUpperCase(result);
    }

    @AfterThrowing(
            pointcut = "execution(* com.example.demo.rest.InstructorRestController.get* (..))",
            throwing = "exception"
    )
    public void afterThrowingGetPrefixFunction(JoinPoint joinPoint, Throwable exception) {
        System.out.println("\n=====>>> Executing LoggingAspect @AfterThrowing advice on any get prefix function");

        String methodSignature = joinPoint.getSignature().toString();

        System.out.println("Method: " + methodSignature);

        System.out.println("Exception: " + exception);
    }

    // does not care about success or failure, work similar to finally in try catch block
    @After("execution(* com.example.demo.rest.InstructorRestController.get* (..))")
    public void afterFinallyGetPrefixFunction(JoinPoint joinPoint) {
        System.out.println("\n=====>>> Executing LoggingAspect @After (finally) advice on any get prefix function");

        String methodSignature = joinPoint.getSignature().toString();

        System.out.println("Method: " + methodSignature);
    }

    @Around("execution(* com.example.demo.rest.InstructorRestController.get* (..))")
    public Object aroundGetPrefixFunction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println(
                "\n=====>>> Executing LoggingAspect @Around (= Before + After) advice on any get prefix function");

        String methodSignature = proceedingJoinPoint.getSignature().toString();
        System.out.println("Method: " + methodSignature);

        long begin = System.currentTimeMillis();
        Object result = null;

        try {
            // Execute function
            result = proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            // Create custom error message
            String customErrorMessage = exception.getMessage() + ". This was customized by @Around advice";

            // Throw custom exception
            throw new RuntimeException(customErrorMessage);
        } finally {
            long end = System.currentTimeMillis();
            long duration = end - begin;

            System.out.println("Duration: " + duration + " milliseconds");
        }

        return result;
    }

    private void convertNameToUpperCase(Object result) {
        if (result instanceof Instructor instructor) {
            String theUpperCaseFirstName = instructor.getFirstName().toUpperCase();

            instructor.setFirstName(theUpperCaseFirstName);
        }
    }
}
