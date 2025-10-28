package com.world.web.utility;

import java.util.concurrent.CompletableFuture;

public class CompletableFuturesUtility {

    public static int compute(int num) {
        return num;
    }

    public static int transform(int num) {

        if (Math.random() < 0.5) {
            System.out.println("Method called failed!!!");
            throw new RuntimeException("Something went wrong");
        }
        return num * 2;
    }

    public static int increment(int num) {
        return num + 1;
    }

    public static int handleException(Throwable throwable) {
        System.out.println("Exception message :: " + throwable.getMessage());
        return 100;
    }

    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() -> compute(10))
                .thenApply(CompletableFuturesUtility::transform)
                .exceptionally(CompletableFuturesUtility::handleException)
                .thenApply(CompletableFuturesUtility::increment)
                .thenAccept(System.out::println);
    }


}
