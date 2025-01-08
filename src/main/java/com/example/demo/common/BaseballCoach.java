package com.example.demo.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BaseballCoach implements Coach {
    public BaseballCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @PostConstruct
    public void doSomething() {
        System.out.println("In doSomething(): " + getClass().getSimpleName());
    }

    // PreDestroy only work for singleton
    @PreDestroy
    public void doCleanup() {
        System.out.println("In doCleanup(): " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Practice baseball for 30 minutes";
    }
}
