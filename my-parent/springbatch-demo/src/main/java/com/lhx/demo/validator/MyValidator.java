package com.lhx.demo.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MyValidator implements JobParametersValidator {
    @Override
    public void validate(@Nullable JobParameters var1) throws JobParametersInvalidException{
        String name = var1.getString("name");
        if(!"lhx".equals(name)){
            throw  new JobParametersInvalidException("name参数不等于【lhx】");
        }
    }
}
