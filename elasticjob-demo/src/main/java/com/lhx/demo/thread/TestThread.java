package com.lhx.demo.thread;

import org.springframework.stereotype.Component;

@Component
public class TestThread implements  Runnable {

    @Override
    public void run() {
        int i=0;
        while(i<10){
            i++;
            System.out.println("Thread-"+Thread.currentThread());
        }
    }

}
