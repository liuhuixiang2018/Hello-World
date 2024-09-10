package com.lhx.demo.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class CommonLinerRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ListableJobLocator jobLocator;

    public void run(String... args)throws Exception {
        //设置任务参数
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("name","lhx");
        jobParametersBuilder.addString("age","28");
        jobParametersBuilder.addString("curTime",new SimpleDateFormat("yyyymmddHHMMSS").format(new Date()));
        Job job = jobLocator.getJob("yourJob");
        JobExecution run  = jobLauncher.run(job,jobParametersBuilder.toJobParameters());
        System.out.println("status--------"+run.getStatus());
    };
}
