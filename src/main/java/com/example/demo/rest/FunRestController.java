package com.example.demo.rest;

import com.example.demo.common.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    private final Coach myCoach;
    private final Coach anotherCoach;
    private final Coach mySwimCoach;

    @Value("${coach.name}")
    private String coachName;

    @Value("${team.name}")
    private String teamName;

    // constructor injection
    @Autowired
    public FunRestController(@Qualifier("baseballCoach") Coach theCoach, @Qualifier("baseballCoach") Coach theAnotherCoach, @Qualifier("aquatic") Coach swimCoach) {
        System.out.println("In constructor: " + getClass().getSimpleName());
        myCoach = theCoach;
        anotherCoach = theAnotherCoach;
        mySwimCoach = swimCoach;
    }

    // setter injection
    // @Autowired
    // public void setCoach(Coach theCoach) {
    //     myCoach = theCoach;
    // }

    @GetMapping("/")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/daily-workout")
    public String getDailyWorkout() {
        return mySwimCoach.getDailyWorkout();
    }

    @GetMapping("/team-info")
    public String getTeamInfo() {
        return "Coach: " + coachName + ", Team: " + teamName;
    }

    @GetMapping("/check")
    public String check() {
        return "Comparing beans: myCoach == anotherCoach, " + (myCoach == anotherCoach);
    }
}
