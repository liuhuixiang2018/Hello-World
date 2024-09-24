package com.lhx.demo.Job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lhx.demo.custom.ElasticSimpleJobInterface;
import org.springframework.stereotype.Component;

import java.util.Date;

@ElasticSimpleJobInterface(jobName = "MyJob",shardingTotalCount = 3,cron = "0/5 * * * * ? *")//每5秒钟执行一次
@Component
public class MyJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {
        Thread.currentThread().setName("MyJob");
        switch (context.getShardingItem()) {
            case 0:
                System.out.println("执行分片0，dateTime：" + new Date(System.currentTimeMillis()));
                break;
            case 1:
                System.out.println("执行分片1，dateTime：" + new Date(System.currentTimeMillis()));
                break;
            default:
                System.out.println("执行分片其他，dateTime：" + new Date(System.currentTimeMillis()));
                break;

        }
    }
}

