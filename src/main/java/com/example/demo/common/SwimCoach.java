package com.example.demo.common;

// SwimCoach did not have @Component. Instead, using @Bean -> use when work with 3rd
public class SwimCoach implements Coach {
    public SwimCoach() {
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Go to swim 30 minutes";
    }
}
