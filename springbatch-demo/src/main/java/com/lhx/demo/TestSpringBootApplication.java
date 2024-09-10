package com.lhx.demo;


import com.lhx.demo.common.CommonLinerRunner;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 使用@EnableBatchProcessing注解后，
 * SpringBoot会自动加载JobLauncher JobBuilderFactory StepBuilderFactory 类并创建对象交给容器管理
 */
@EnableBatchProcessing
@SpringBootApplication
public class TestSpringBootApplication extends CommonLinerRunner {

         public static void main(String [] args){
         SpringApplication.run(TestSpringBootApplication.class,args);
         System.out.println("---------------TestSpringBootApplication---------------------");
     }

     public void run(String... args)throws Exception {
         System.out.println("---------------TestSpringBootApplication  run---------------------");
         super.run(args);
     };
}
