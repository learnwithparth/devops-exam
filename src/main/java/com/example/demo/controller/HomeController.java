package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/")
    public @ResponseBody String greeting() {
        return "<h1>Hello, This is from t2.mirco server, My Name is Parth Mangukiya from charusat and ID is 20it066 <br>Welome to spring CI/CD</h1>";
    }

}
