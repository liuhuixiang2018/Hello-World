package com.lhx.demo.service;

import org.apache.catalina.connector.Request;
import org.springframework.stereotype.Service;

@Service
public interface MyService {


    public boolean order(Request request);
    public String callService();
}