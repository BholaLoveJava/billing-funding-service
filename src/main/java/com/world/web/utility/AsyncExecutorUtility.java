package com.world.web.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecutorUtility {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            System.out.println("Task started on Thread :: " + Thread.currentThread().getName());
        });

        System.out.println("Main thread continues...");
        executorService.shutdown();
    }
}

/*
 * ExecutorService
 * Pros: Thread pooling, Re-usability, Better performance.
 * Cons: Still manual handling of Future results.
 */