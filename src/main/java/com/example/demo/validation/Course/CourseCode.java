package com.example.demo.validation.Course;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourseCodeConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
// RetentionPolicy.RUNTIME: process it at runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {
    public String value() default "JAVA";

    public String message() default "must start with JAVA";

    // groups related constraint
    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default  {};
}

