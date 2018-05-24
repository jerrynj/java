package com.example;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World211112";
    }

    @RequestMapping("/h")
    public String h() {
        return "Hhhhhh888888888889999 www  ";
    }
}