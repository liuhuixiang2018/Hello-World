package com.lhx.demo.config;


import org.apache.shardingsphere.elasticjob.lite.internal.schedule.JobScheduler;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {

        @Autowired
        @Qualifier("myJob")
        private SimpleJob myJob;

        @Bean(initMethod = "init")
        public JobScheduler jobDemo() {
            System.out.println("jobDemo");
            // 注册中心、job配置，JobScheduler init 方法启动
            JobScheduler jobScheduler = new JobScheduler(createRegistryCenter(),createJobConfiguration());
            return jobScheduler;
        }


        private CoordinatorRegistryCenter createRegistryCenter() {
            // zookeeper 服务器列表 "localhost:2181"
            // zookeeper 命名空间 "elastic-job-demo-zoo"
            CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.116.128:2181"
                    , "elastic-job-demo-zoo"));
            regCenter.init();
            return regCenter;
        }

        private LiteJobConfiguration createJobConfiguration() {
            // 定义作业核心配置
            // 作业名称："demoSimpleJob"、 CRON 表达式，控制作业触发时间："0/15 * * * * ?"、 作业分片总数：2
            JobCoreConfiguration simpleCoreConfig =
                    JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 2).build();
            // 定义 SIMPLE 类型配置
            SimpleJobConfiguration simpleJobConfig =
                    new SimpleJobConfiguration(simpleCoreConfig, myJob.getClass().getCanonicalName());
            // 定义 Lite 作业根配置
            LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
            return simpleJobRootConfig;
        }
}

