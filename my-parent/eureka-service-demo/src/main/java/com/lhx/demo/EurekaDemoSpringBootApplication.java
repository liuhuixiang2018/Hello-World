package com.lhx.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaDemoSpringBootApplication {

         public static void main(String [] args){
             SpringApplication.run(EurekaDemoSpringBootApplication.class,args);
             System.out.println("---------------EurekaDemoSpringBootApplication---------------------");
     }

}
