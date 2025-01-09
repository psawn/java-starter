package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoSecurityController {
    @GetMapping("/security")
    public String demoSecured() {
        return "security/home";
    }

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "security/plain-login";
    }

    @GetMapping("/leaders")
    public String showLeaders() {
        return "security/leaders";
    }

    @GetMapping("/systems")
    public String showSystems() {
        return "security/systems";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "security/access-denied";
    }
}
