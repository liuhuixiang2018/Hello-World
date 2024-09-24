package com.lhx.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @RequestMapping("/api/elasticjob/")
    public String getUserId(){
        System.out.println("elasticjob-/api/elasticjob/");
        return "elasticjob";
    }
}
