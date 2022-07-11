package com.shishodia.multithreading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TerminateThreadApproachOne {

    public static void main(String[] args) throws Exception {

        Integer terminateThreadInSeconds = 3; // Thread should complete the execution within this time.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(new Task());

        try {
            System.out.println(String.format("Started. Thread will terminate if not successfully executed within %d seconds.", terminateThreadInSeconds));
            
            /*
             * NOTE: Future.get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException
             * Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available.
            */ 
            System.out.println(future.get(terminateThreadInSeconds, TimeUnit.SECONDS)); 
            
            System.out.println(String.format("Finished! Thread executed within %d.", terminateThreadInSeconds));
        } catch (TimeoutException e) {
            /*
             * NOTE: Future.cancel(boolean mayInterruptIfRunning)
             * Attempts to cancel execution of this task. This attempt will fail if the task has already completed, 
             * has already been cancelled, or could not be cancelled for some other reason.
             */
            future.cancel(true);
            System.out.println(String.format("Timeout! Thread was not able to complete execution within %d seconds.", terminateThreadInSeconds));
        }

        /* 
         * Attempts to stop all actively executing tasks, halts the processing of 
         * waiting tasks, and returns a list of the tasks that were awaiting execution.
         */
        executor.shutdownNow();
    }
}

class Task implements Callable<String> {
    @Override
    public String call() throws Exception {
        Integer timeToExecuteThreadInSeconds = 5;
        Thread.sleep(timeToExecuteThreadInSeconds * 1000);
        return String.format("Thread executed successfully in %d seconds.", timeToExecuteThreadInSeconds);
    }
}