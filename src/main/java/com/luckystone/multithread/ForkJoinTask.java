package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * https://juejin.im/post/59be875e5188257e6b6d91c1
 * ForkJoin Demo
 * 如何充分利用多核CPU，计算很大List中所有整数的和？
 */
public class ForkJoinTask {

    static class ComputeTask extends RecursiveTask<Integer> {

        public static final int DEFAULT_THRESHOLD = 1000;
        private int left, right;
        private int threshold;
        private int[] list;

        ComputeTask(int left, int right, int threshold, int[] list) {
            this.list = list;
            this.left = left;
            this.right = right;
            this.threshold = threshold; //任务划分的最小值（若为1000则含义是Fork到小于或等于1000就不再继续Fork了）
        }

        @Override
        protected Integer compute() {
            if (right - left <= threshold) {
                int sum = 0;
                for (int i = left; i < right; i++) {
                    sum += list[i];
                }
                return sum;
            } else {
                int middle = left + (right - left) / 2;
                //System.out.println("middle: " + middle);
                ComputeTask leftHandTask = new ComputeTask(left, middle, threshold, list); //左任务
                ComputeTask rightHandTask = new ComputeTask(middle, right, threshold, list); //右任务
                leftHandTask.fork();
                rightHandTask.fork();
                return leftHandTask.join() + rightHandTask.join();
            }
        }
    }

    public int simpleCompute(int [] list) {
        //简单粗暴的做法
        int sum = 0;
        for (int i = 0; i < list.length; i++) {
            sum += list[i];
        }
        return sum;
    }

    public int forkJoinCompute(ForkJoinPool forkJoinPool, int [] list) throws InterruptedException, ExecutionException {
        //简单粗暴的做法
        //Fork/Join的做法
        ComputeTask task = new ComputeTask(0, list.length, 10_000, list);
        Future<Integer> future = forkJoinPool.submit(task); //提交Task
        return future.get();
    }


    public static void main(String... args) throws InterruptedException, ExecutionException{
        int[] list = IntStream.rangeClosed(0, 100_000_000).toArray();
        int count = 100;
        int sum = 0;

        ForkJoinTask task = new ForkJoinTask();

        Date start = new Date();
        for(int i=0;i<count;i++){
            sum = task.simpleCompute(list);
        }
        System.out.println("simpleSum: " + sum);
        Date end = new Date();
        System.out.println("finish！execute time=" + (end.getTime()-start.getTime()) + "ms");

        ForkJoinPool forkJoinPool = new ForkJoinPool(); //起一个数量等于可用CPU核数的池子（对应题目中“充分利用多核”）
        start = new Date();
        for(int i=0;i<count;i++){
            sum = task.forkJoinCompute(forkJoinPool, list);
        }
        System.out.println("forkJoinSum: " + sum);
        end = new Date();
        System.out.println("finish！execute time=" + (end.getTime()-start.getTime()) + "ms");
        forkJoinPool.shutdown(); //关闭池子

    }

}
