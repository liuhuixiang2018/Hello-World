package com.lhx.demo.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuhuixiang
 * @Description: Job任务注册配置类
 * @Date: 14:53 2024/09/06
 */
@Configuration
public class JobRegistryConfig {

    @Autowired
    private JobRegistry jobRegistry;
    /**装载context*/
    @Autowired
    private ApplicationContext context;//或者implements ApplicationContextAware 通过构造注入

    @Bean
    public JobRegistryBeanPostProcessor registryBeanFileProcessor() throws Exception {
        JobRegistryBeanPostProcessor registryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        registryBeanPostProcessor.setJobRegistry(jobRegistry);
        registryBeanPostProcessor.setBeanFactory(context.getAutowireCapableBeanFactory());
        registryBeanPostProcessor.afterPropertiesSet();
        return registryBeanPostProcessor;
    }
}
