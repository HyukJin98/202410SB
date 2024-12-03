package edu.du.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/management")
    public String managementPage(){
        return "management";
    }

    @GetMapping("/academic")
    public String academicPage() {
        return "academic";
    }

    @GetMapping("/course")
    public String coursePage() {
        return "course";
    }

    @GetMapping("/grades")
    public String gradesPage() {
        return "grades";
    }

    @GetMapping("/scholarship")
    public String scholarshipPage() {
        return "scholarship";
    }
}

