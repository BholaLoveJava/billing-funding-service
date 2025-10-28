package com.world.web.serviceImpl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncProcessorService {

    @Async
    public CompletableFuture<String> processAsyncTask() {
        try {
            Thread.sleep(1000);
        }catch(InterruptedException exp){
           System.out.println("Exception message :: "+exp.getMessage());
        }
        return CompletableFuture.completedFuture("Task Completed!!");
    }
}
