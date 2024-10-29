package edu.du.sb1029.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeginController {
    @GetMapping("/")
    public String begin() {
        return "/sample/basic";
    }
}
