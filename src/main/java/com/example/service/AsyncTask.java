package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsyncTask {

 @Async("myTaskAsyncPool") //myTaskAsynPool即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
  public void doTask1(int i) throws InterruptedException{
   log.info("Task"+i+" started.");
  }
} 