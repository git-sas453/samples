package com.sas.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 3000, TimeUnit.MILLISECONDS, queue);

        pool.execute(new TaskThread());
    }

    public static class TaskThread implements Runnable {
        @Override
        public void run() {
            int a = 1;
            int b = 0;

            try {
                int c = a/b;
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw e;
            } finally {
                System.out.println("final");
            }
        }
    }
}
