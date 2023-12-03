package edu.hw8.Task2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

public final class FixedThreadPool implements ThreadPool {

    private final List<Thread> workerThreads;
    private final Queue<Runnable> taskQueue;
    private final Lock queueLock = new ReentrantLock();
    private final Condition queueIsNotEmpty = queueLock.newCondition();
    private boolean isRunning = true;

    private FixedThreadPool(int numberOfThreads) {
        taskQueue = new LinkedList<>();
        workerThreads = Stream.generate(() -> new Thread(() -> {
            try {
                this.work();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        })).limit(numberOfThreads).toList();
    }

    public static FixedThreadPool create(int numberOfThreads) {
        return new FixedThreadPool(numberOfThreads);
    }

    private void work() throws InterruptedException {
        while (true) {
            Runnable task;
            boolean shouldReturn = false;
            queueLock.lock();
            try {
                while (isRunning && taskQueue.isEmpty()) {
                    queueIsNotEmpty.await();
                }
                if (!isRunning && taskQueue.isEmpty()) {
                    shouldReturn = true;
                }
                task = taskQueue.poll();
            } finally {
                queueLock.unlock();
            }
            if (task != null) {
                task.run();
            }
            if (shouldReturn) {
                return;
            }
        }
    }

    @Override
    public void start() {
        queueLock.lock();
        try {
            isRunning = true;
        } finally {
            queueLock.unlock();
        }
        workerThreads.forEach(Thread::start);
    }

    @Override
    public void execute(Runnable runnable) {
        queueLock.lock();
        try {
            taskQueue.add(runnable);
            queueIsNotEmpty.signalAll();
        } finally {
            queueLock.unlock();
        }
    }

    @Override
    public void close() throws InterruptedException {
        queueLock.lock();
        try {
            isRunning = false;
            queueIsNotEmpty.signalAll();
        } finally {
            queueLock.unlock();
        }
        for (var thread : workerThreads) {
            thread.join();
        }
    }

}
