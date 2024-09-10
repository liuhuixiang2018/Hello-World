package com.lhx.demo.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 实现接口方式的监听
 */
@Component
public class MyListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution var1){
        System.out.println("我的监听器------before------"+var1.getStatus());
    }
    @Override
    public void afterJob(JobExecution var1){
        System.out.println("我的监听器------after------"+var1.getStatus());

    }
}
