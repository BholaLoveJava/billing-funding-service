package com.world.web.utility;

public class AsyncUtility {

    public static void main(String[] args) {

        Runnable runnableTask = () -> {
            try {
                System.out.println("Task started on thread :: "+Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException exp) {
                System.out.println("Exception message :: "+exp.getMessage());
            }
            System.out.println("Task Finished!!!");
        };

        Thread thread = new Thread(runnableTask, "thread-one");
        thread.start();

        System.out.println("Main thread continues...");
    }
}

/*
 *Pros: Simple to understand.
 *Cons: Manual thread management is not scalable.
 */