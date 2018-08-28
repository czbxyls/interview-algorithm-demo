package com.luckystone.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/***
 * 参考：http://www.importnew.com/21312.html
 * 异步模式：FutureTask实现
 */
public class FutureDemo2 {

    public class RealData implements Callable<String> {
        private String para;

        public RealData(String para) {
            this.para = para;
        }

        public String call() throws Exception {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 10; i++) {
                sb.append(para);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
            return sb.toString();
        }
    }

    public FutureTask<String> request(final String queryStr) {
        FutureTask<String> future = new FutureTask<String>(new RealData(queryStr));
        return future;
    }

    public static void main(String[] args) throws Exception{
        FutureDemo2 demo = new FutureDemo2();
        FutureTask<String> future = demo.request("hellwo");
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // 执行FutureTask，相当于上例中的 client.request("a") 发送请求
        // 在这里开启线程进行RealData的call()执行
        executor.submit(future);
        System.out.println("请求完毕");
        try {
            // 这里依然可以做额外的数据操作，这里使用sleep代替其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        // 相当于data.getResult ()，取得call()方法的返回值
        // 如果此时call()方法没有执行完成，则依然会等待
        System.out.println("数据 = " + future.get());
    }

}
