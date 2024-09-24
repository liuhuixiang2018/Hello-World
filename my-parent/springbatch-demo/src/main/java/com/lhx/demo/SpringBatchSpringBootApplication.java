package com.lhx.demo;


import com.lhx.demo.common.CommonLinerRunner;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用@EnableBatchProcessing注解后，
 * SpringBoot会自动加载JobLauncher JobBuilderFactory StepBuilderFactory 类并创建对象交给容器管理
 */
@EnableEurekaClient
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchSpringBootApplication /*extends CommonLinerRunner */{

         public static void main(String [] args){
         SpringApplication.run(SpringBatchSpringBootApplication.class,args);
         System.out.println("---------------SpringBatchSpringBootApplication---------------------");
     }

    /* public void run(String... args)throws Exception {
         System.out.println("---------------TestSpringBootApplication  run---------------------");
         super.run(args);
     };*/
}
