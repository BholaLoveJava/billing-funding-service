package com.world.web.utility;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureUtility {

    public static void main(String[] args) {

        /*
         * CompletableFuture gives three main ways to handle exceptions
         * 1. exceptionally()
         * 2. handle()
         * 3. whenComplete()
         */

        /* exceptionally()
         * catches exception and returns a fallback value
         * similar to a catch block
         */
        CompletableFuture<Integer> futureResult = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) {
                throw new RuntimeException("Something went wrong");
            }
            return 10;
        }).exceptionally(exp -> {
            System.out.println("Exception message :: " + exp.getMessage());
            return 0;
        });

        Integer result = futureResult.join();
        System.out.println("Result :: " + result);

        /*
         * handle()
         * Handles both normal and exception result in one place
         */
        CompletableFuture<Integer> cfResult =
                CompletableFuture.supplyAsync(() -> {
                    return 10 / 0;
                }).handle((data, exp) -> {
                    if (exp != null) {
                        System.out.println("Exception message :: " + exp.getMessage());
                        return -1;
                    }
                    return data;
                });
        System.out.println("CompletableFuture result :: " + cfResult.join());

        /*
         * whenComplete()
         * Allows logging or side-effects when computation completes
         * Doesn't change the result unless, you re-throw it
         */
        CompletableFuture<Integer> cfCompResult =
           CompletableFuture.supplyAsync(() -> {
               return 10 / 0;
           }).whenComplete((response, exp) -> {
               if(exp != null) {
                   System.out.println("Exception message :: "+ exp.getMessage());
                   throw new RuntimeException(exp.getMessage());
               }
           }).exceptionally(exp -> {
               return 0;
           });
        System.out.println("Completable Future result :: "+ cfCompResult.join());
    }
}

/*
 * CompletableFuture Best practices
 * Use handle() when you want to recover and continue.
 * Use exceptionally() when you want a fallback/default value.
 * whenComplete() for logging.
 */