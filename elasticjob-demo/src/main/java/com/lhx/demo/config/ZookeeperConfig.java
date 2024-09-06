package com.lhx.demo.config;


import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zk的配置
 */
@Configuration
public class ZookeeperConfig {
    //zookeeper服务地址
    @Value("${zookeeper.connString}")
    private  String ZOOKEEPER_CONNECTION_STRING ;

    //定时任务命名空间
    @Value("${myjob.namespace}")
    private  String JOB_NAMESPACE;
    @Autowired
    private ElasticJobConfig elasticJobConfig;

    //zk的配置及创建注册中心
    @Bean(initMethod = "init")
    public  ZookeeperRegistryCenter setUpRegistryCenter(){
        System.out.println("测试循环依赖是否解决：ZookeeperConfig init ---------- elasticJobConfig"+elasticJobConfig.toString());
        //zk的配置
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);
        //创建注册中心
        ZookeeperRegistryCenter zookeeperRegistryCenter = new
                ZookeeperRegistryCenter(zkConfig);
        return zookeeperRegistryCenter;
    }
}

