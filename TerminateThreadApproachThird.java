package com.shishodia.multithreading;

import java.util.concurrent.TimeUnit;

class TerminateThread extends Thread {
    @Override
    public void run() {
        // We can place a time consuming logic here in this method.
        Integer timeToExecuteThreadInSeconds = 1;
        while (!Thread.interrupted()) {
            try {
                System.out.println("Thread is running uninterrupted. It is busying doing some work.");
                TimeUnit.SECONDS.sleep(timeToExecuteThreadInSeconds);
            } catch (InterruptedException e) {
                System.out.println("Exception: Thread is interrupted by thread.interrupt(). Exit.");
                return;
            }
            System.out.println("Post complex logic.");
        }
        // System.out.println("Thread execution stopped. thread.interrupt() called.");
    }
}

public class TerminateThreadApproachThird {

    public static void main(String[] args) {

        Integer timeToExecuteThreadInSeconds = 4;
        TerminateThread thread = new TerminateThread();

        thread.start();
        System.out.println(String.format("Started! Interrupt thread after %d seconds.", timeToExecuteThreadInSeconds));

        try {
            Thread.sleep(timeToExecuteThreadInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // This will 'Thread.interrupted()'.
        thread.interrupt();
    }
    
}