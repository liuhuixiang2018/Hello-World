package com.lhx.demo.Job;


import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Qualifier("myJob")
public class MyJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0:
                System.out.println("执行分片0，dateTime：" + new Date(System.currentTimeMillis()));
                break;
            case 1:
                System.out.println("执行分片1，dateTime：" + new Date(System.currentTimeMillis()));
                break;
        }
    }
}

