package com.lhx.demo.job;

import com.lhx.demo.listener.MyAnnoListener;
import com.lhx.demo.listener.MyListener;
import com.lhx.demo.validator.MyValidator;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class MyBatchJob {
    //job调度器
    @Autowired
    private JobLauncher jobLauncher;
    //job构造器工厂
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    //step构造器工厂
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    //自定义参数校验器
    @Autowired
    private MyValidator myValidator;
    @Autowired
    private MyListener myListener;
    @Autowired
    private MyAnnoListener myAnnoListener;

    private static final DefaultJobParametersValidator defaultJobParametersValidator = new DefaultJobParametersValidator();

    private static final CompositeJobParametersValidator compositeJobParametersValidator = new CompositeJobParametersValidator();

    @Bean("myJob")
    public Job myJob(){
        System.out.println("myJob11000000000000000000000000");
        //defaultJobParametersValidator.setRequiredKeys(new String[]{"name","curTime"});
        defaultJobParametersValidator.setOptionalKeys(new String[]{"age"});
        //List<JobParametersValidator> validatorList = new ArrayList<>();
        compositeJobParametersValidator.setValidators(Arrays.asList(defaultJobParametersValidator,myValidator));
        try {
            compositeJobParametersValidator.afterPropertiesSet();//判断校验器是否为null
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return jobBuilderFactory.get("myJob").start(myStep())
                //添加组合参数校验器
                .validator(compositeJobParametersValidator)
                //参数增量器(run.id自增)
                .incrementer(new RunIdIncrementer()).listener(myListener).listener(JobListenerFactoryBean.getListener(myAnnoListener)).build();
    }
    @Bean
    public Step myStep(){
        System.out.println("myStep111000000000000000000000000");
        return stepBuilderFactory.get("myStep").tasklet(myTasklet(null)).build();
    }

    /**
     * des:
     *  @StepScope注解 表示在启动项目的时候，不加载该Step步骤bean，等myStep()被调用时才加载。这就是所谓延时获取。
     * @return
     */
    @StepScope
    @Bean
    public Tasklet myTasklet(@Value("#{jobParameters['name']}") String name){
        System.out.println("myTasklet 111000000000000000000000000");
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("进入tasklet中.............");
                //方案1：使用ChunkContext类 获取job参数
                Map<String, Object> params =  chunkContext.getStepContext().getJobParameters();
                System.out.println("方案1：使用ChunkContext类 获取job参数 params-----"+params.toString());
                //TODO 方案二：@Value延时获取  此方式获取为空
                System.out.println("方案2：使用@Value延时获取 获取job参数name-----"+name);
                return  RepeatStatus.FINISHED;
            }
        };
    }
}
