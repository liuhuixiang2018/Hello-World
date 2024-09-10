package com.lhx.demo.job;

import com.lhx.demo.listener.MyAnnoListener;
import com.lhx.demo.listener.MyListener;
import com.lhx.demo.validator.MyValidator;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
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
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class YourBatchJob {
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

    @Bean("yourJob")
    public Job yourJob(){
        System.out.println("yourJob11000000000000000000000000");
        //defaultJobParametersValidator.setRequiredKeys(new String[]{"name","curTime"});
        defaultJobParametersValidator.setOptionalKeys(new String[]{"age"});
        //List<JobParametersValidator> validatorList = new ArrayList<>();
        compositeJobParametersValidator.setValidators(Arrays.asList(defaultJobParametersValidator,myValidator));
        try {
            compositeJobParametersValidator.afterPropertiesSet();//判断校验器是否为null
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return jobBuilderFactory.get("yourJob").start(yourStep())
                //添加组合参数校验器
                .validator(compositeJobParametersValidator)
                //参数增量器(run.id自增)
                .incrementer(new RunIdIncrementer()).listener(myListener).listener(JobListenerFactoryBean.getListener(myAnnoListener)).build();
    }
    @Bean
    public Step yourStep(){
        System.out.println("yourStep111000000000000000000000000");
        return stepBuilderFactory.get("yourStep").chunk(2)
                .reader(yourReader()).processor(yourProcessor()).writer(yourWriter()).build();
    }
    @Bean
    public ItemReader yourReader(){
        return new ItemReader() {
            @Override
            public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                System.out.println("yourReader--------");
                return "yourReader";
            }
        };
    }
    @Bean
    public ItemProcessor yourProcessor(){
        return new ItemProcessor() {
            @Override
            public Object process(Object o) throws Exception {
                System.out.println("yourProcessor--------");
                return "yourProesssor";
            }
        };
    }
    @Bean
    public ItemWriter yourWriter(){
        return new ItemWriter() {
            @Override
            public void write(List list) throws Exception {
                System.out.println("yourWriter--------"+list.toString());
            }
        };
    }

}
