package com.world.web.utility;

import java.util.concurrent.CompletableFuture;

public class AsyncCompletableFuture {

    public static void main(String[] args) {

        CompletableFuture.runAsync(() -> {
            System.out.println("Running task in :: " + Thread.currentThread().getName());
        }).thenRun(() -> {
            System.out.println("Task finished!!");
        });

        CompletableFuture.supplyAsync(() -> {
            System.out.println("Running task in :: "+Thread.currentThread().getName());
            return "Hello from async";
        }).thenAccept(result -> {
            System.out.println("Task finished with result :: "+result);
        });

        System.out.println("Main thread continues...");
    }
}

/*
 * CompletableFuture
 * Pros: Non-blocking, Chaining tasks, Exception Handling with .exceptionally().
 * Cons: Requires understanding of futures and callbacks.
 */