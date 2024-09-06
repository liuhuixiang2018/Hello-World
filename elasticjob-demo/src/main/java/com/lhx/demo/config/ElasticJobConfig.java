package com.lhx.demo.config;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lhx.demo.custom.ElasticSimpleJobInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ElasticJob配置
 */
@Configuration
public class ElasticJobConfig {

    @Autowired
    ZookeeperRegistryCenter registryCenter;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * 配置任务详细信息
     *
     * @param jobName            任务执行名称
     * @param obj                任务实例
     * @param cron               执行策略
     * @param shardingTotalCount 分片数量
     * @return
     */
    private LiteJobConfiguration createJobConfiguration(final String jobName,final Object obj,
                                                        final String cron,
                                                        final int shardingTotalCount) {
        //创建JobCoreConfigurationBuilder
        JobCoreConfiguration.Builder JobCoreConfigurationBuilder =
                JobCoreConfiguration.newBuilder(jobName, cron, shardingTotalCount);
        //创建JobCoreConfiguration
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfigurationBuilder.build();
        //创建SimpleJobConfiguration
        SimpleJobConfiguration simpleJobConfiguration =
                new SimpleJobConfiguration(jobCoreConfiguration, obj.getClass().getCanonicalName());
        //创建LiteJobConfiguration ,使用平均分片策略 com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).jobShardingStrategyClass("com.dangdang.ddframe.job.lite.api.strategy.impl.AverageAllocationJobShardingStrategy").overwrite(true)
                .build();
        return liteJobConfiguration;
    }

    @PostConstruct
    public List<SpringJobScheduler>  init() {
        System.out.println("-----------------start--"+registryCenter.toString());
        List<SpringJobScheduler> list = new ArrayList<>();
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(ElasticSimpleJobInterface.class);
        for (Map.Entry<String, Object> s : beanMap.entrySet()) {
            Class<?>[] inters = s.getValue().getClass().getInterfaces();
            for (Class<?> cla : inters) {
                if (cla == SimpleJob.class) {
                    System.out.println(s.getKey() + "类使用了ElasticSimpleJobInterface注解,实现了" + cla.toString() + "接口");
                    Annotation anno = s.getValue().getClass().getAnnotation(ElasticSimpleJobInterface.class);
                    //创建SpringJobScheduler
                    SpringJobScheduler springJobScheduler =  new SpringJobScheduler((ElasticJob) s.getValue(),registryCenter,createJobConfiguration(((ElasticSimpleJobInterface) anno).jobName(),s,((ElasticSimpleJobInterface) anno).cron(),((ElasticSimpleJobInterface) anno).shardingTotalCount()));
                    springJobScheduler.init();
                    list.add(springJobScheduler);
                }
            }
        }
        System.out.println("-----------------end--"+registryCenter.toString());
        return list;
    }
}

