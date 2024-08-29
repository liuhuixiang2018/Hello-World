package com.lhx.demo.controller;

import com.lhx.demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    MyService myService;
    @GetMapping("/word")
    public String word(){
        return myService.callService();
    }
    @GetMapping("/hello")
    public String hello(){
        return "<h1 style='color: red; text-align: center'>Hello Spring Boot World~</h1>";
    }
}
