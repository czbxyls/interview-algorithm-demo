package com.luckystone.multithread;

/***
 * 参考：http://www.importnew.com/21312.html
 * 异步模式: 自定义实现
 */
public class FutureDemo {

    public interface Data {
        public String getResult ();
    }

    public class FutureData implements Data {
        protected RealData realdata = null;   //FutureData是RealData的包装
        protected boolean isReady = false;
        public synchronized void setRealData(RealData realdata) {
            if (isReady) {
                return;
            }
            this.realdata = realdata;
            isReady = true;
            notifyAll();    //RealData已经被注入，通知getResult()
        }
        public synchronized String getResult()//会等待RealData构造完成
        {
            while (!isReady) {
                try {
                    wait();    //一直等待，知道RealData被注入
                } catch (InterruptedException e) {
                }
            }
            return realdata.result;  //由RealData实现
        }
    }

    public class RealData implements Data {
        protected final String result;
        public RealData(String para) {
            // RealData的构造可能很慢，需要用户等待很久，这里使用sleep模拟
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 10; i++) {
                sb.append(para);
                try {
                    // 这里使用sleep，代替一个很慢的操作过程
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            result = sb.toString();
        }
        public String getResult() {
            return result;
        }
    }

    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        new Thread() {
            public void run()
            {
                // RealData的构建很慢，
                //所以在单独的线程中进行
                RealData realdata = new RealData(queryStr);
                future.setRealData(realdata);
            }
        }.start();
        return future; // FutureData会被立即返回
    }

    public static void main(String[] args) {
        FutureDemo client = new FutureDemo();
        // 这里会立即返回，因为得到的是FutureData而不是RealData
        Data data = client.request("name");
        System.out.println("请求完毕");

        try {
            // 这里可以用一个sleep代替了对其他业务逻辑的处理
            // 在处理这些业务逻辑的过程中，RealData被创建，从而充分利用了等待时间
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        // 使用真实的数据
        System.out.println("数据 = " + data.getResult());
    }

}
