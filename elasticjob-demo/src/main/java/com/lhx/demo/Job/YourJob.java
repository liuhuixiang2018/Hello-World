package com.lhx.demo.Job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lhx.demo.custom.ElasticSimpleJobInterface;
import org.springframework.stereotype.Component;

import java.util.Date;

@ElasticSimpleJobInterface(jobName = "YourJob",shardingTotalCount = 3,cron = "0/10 * * * * ? *")//每10秒钟执行一次
@Component
public class YourJob implements SimpleJob {

    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0:
                System.out.println("YourJob执行分片0，dateTime：" + new Date(System.currentTimeMillis()));
                break;
            case 1:
                System.out.println("YourJob执行分片1，dateTime：" + new Date(System.currentTimeMillis()));
                break;
            default:
                System.out.println("YourJob执行分片其他，dateTime：" + new Date(System.currentTimeMillis()));
                break;

        }
    }
}

