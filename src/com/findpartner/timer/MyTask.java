package com.findpartner.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("mytask") 
public class MyTask {

	//@Scheduled(cron = "0-59 * * * * ?")  
    public void job1() {  
        System.out.println("任务进行中。。。");  
    }  
}
