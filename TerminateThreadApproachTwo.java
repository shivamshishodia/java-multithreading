package com.shishodia.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class TerminateThreadApproachTwo {

    public static void main(String args[]) throws InterruptedException {

        Integer terminateThreadInSeconds = 4; // Thread should complete the execution within this time.
        Integer timeToExecuteThreadInSeconds = 5; // Actual time taken by thread

        /*
        * I am using a lambda for simplicity.
        */
        System.out.println(String.format("Started. Thread will terminate if not successfully executed within %d seconds.", terminateThreadInSeconds));
        CompletableFuture.supplyAsync(() -> {
            // I am taking some time and returning a + b value.
            int a = 10;
            int b = 15;
            try {
                TimeUnit.SECONDS.sleep(timeToExecuteThreadInSeconds);
            } catch (InterruptedException e) {
                System.out.println(String.format("Terminated! Thread was not able to complete execution within %d seconds.", terminateThreadInSeconds));
            }
            String.format("Thread executed successfully in %d seconds.", timeToExecuteThreadInSeconds);
            return a + b;
        })
        .orTimeout(terminateThreadInSeconds, TimeUnit.SECONDS)
        .whenComplete((result, exception) -> {
            if (exception != null) {
                System.out.println(String.format("Timeout! Thread was not able to complete execution within %d seconds.", terminateThreadInSeconds));
                // exception.printStackTrace(); //  Uncomment if needed. 
            } else {
                System.out.println(String.format("The summation result is %d.", result));
            }
        });
        // Waiting as the future is supplied async.
        TimeUnit.SECONDS.sleep(10);
    }

}