package com.lhx.demo.thread;

import java.util.concurrent.*;

/**
 *
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> sharedData = new ConcurrentHashMap<String, String>();
        MyThread thread = new MyThread(sharedData);
        thread.start();
        sharedData.put("key", "value");
        System.out.println("sharedData in main thread: " + sharedData.get("key"));
    }
}
    class MyThread extends Thread {
        ConcurrentHashMap<String, String> sharedData;
        public MyThread(ConcurrentHashMap<String, String> data) {
            this.sharedData = data;
        }
        public void run() {
            sharedData.put("key", "new value");
            System.out.println("sharedData in child thread: " + sharedData.get("key"));
        }
    }

