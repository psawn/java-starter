package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    public String sayHello(Model theModel) {

        theModel.addAttribute("theDate", java.time.LocalDateTime.now());

        return "helloworld";
    }

    //    @RequestMapping("/showForm")
    @GetMapping("/showForm")
    public String showForm() {
        return "helloworld-form";
    }

    @RequestMapping("/processForm")
    public String processForm() {
        return "helloworld-process-form";
    }

    @RequestMapping("/processFormVersion2")
    public String transformVersion2(HttpServletRequest request, Model model) {
        String theName = request.getParameter("studentName");

        theName = theName.toUpperCase();

        String result = "Yo! " + theName;

        model.addAttribute("message", result);

        return "helloworld-process-form";
    }

    @PostMapping("/processFormVersion3")
    public String transformVersion3(@RequestParam("studentName") String theName, Model model) {
        theName = theName.toUpperCase();

        String result = "Hey! " + theName;

        model.addAttribute("message", result);

        return "helloworld-process-form";
    }
}