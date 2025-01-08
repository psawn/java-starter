package com.example.demo.controller;

import com.example.demo.entity.Student;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Value("${countries}")
    private List<String> countries;

    @Value("${languages}")
    private List<String> languages;

    @Value("${systems}")
    private List<String> systems;

    @GetMapping("/showStudentForm")
    public String showForm(Model theModel) {
        Student theStudent = new Student();

        theModel.addAttribute("student", theStudent);
        theModel.addAttribute("countries", countries);
        theModel.addAttribute("languages", languages);
        theModel.addAttribute("systems", systems);

        return "student-form";
    }

    // BindingResult: the result of validation
    @PostMapping("/processStudentForm")
    public String processStudentForm(@Valid @ModelAttribute("student") Student theStudent, BindingResult theBindingResult, Model theModel) {
        System.out.println("Email: " + theStudent.getEmail());
        System.out.println("FirstName: " + theStudent.getFirstName());
        System.out.println("LastName: " + theStudent.getLastName());
        System.out.println("Country: " + theStudent.getCountry());
        System.out.println("Language: " + theStudent.getLanguage());
        System.out.println("Systems: " + theStudent.getSystems());

        System.out.println("Binding result: " + theBindingResult.toString());

        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("countries", countries);
            theModel.addAttribute("languages", languages);
            theModel.addAttribute("systems", systems);

            return "student-form";
        }

        return "student-confirmation";
    }

    // InitBinder: pre-process every req
    // convert trim input string, remove leading and trailing whitespace
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
