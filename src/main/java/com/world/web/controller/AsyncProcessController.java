package com.world.web.controller;

import com.world.web.serviceImpl.AsyncProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/async")
public class AsyncProcessController {

    private final AsyncProcessorService asyncProcessorService;

    @Autowired
    public AsyncProcessController(AsyncProcessorService asyncProcessorService){
        this.asyncProcessorService = asyncProcessorService;
    }

    @GetMapping("/process")
    public String processAsyncTask() {
        //This will execute on Separate Thread, other than Main thread
         asyncProcessorService.processAsyncTask();
         return "Request accepted!!";
    }

}

/*
 * Pros: Easy to integrate with web APIs, automatically runs in background thread.
 * Cons: Works only in Spring context.
 */