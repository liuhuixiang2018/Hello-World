package com.lhx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticJobSpringBootApplication {

    public static void main(String[] args) {
        System.out.println("---------------ElasticJobSpringBootApplication----------start-----------");
        SpringApplication.run(ElasticJobSpringBootApplication.class, args);
        System.out.println("---------------ElasticJobSpringBootApplication-----------end----------");
    }

}
