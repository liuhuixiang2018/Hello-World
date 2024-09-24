package com.lhx.demo.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;

/**
 * 注解方式的监听器
 */
@Component
public class MyAnnoListener {
    @BeforeJob
    public void beforeJob(JobExecution var1){
        System.out.println("我的Anno监听器------before------"+var1.getStatus());
    }
    @AfterJob
    public void afterJob(JobExecution var1){
        System.out.println("我的Anno监听器------after------"+var1.getStatus());

    }
}
