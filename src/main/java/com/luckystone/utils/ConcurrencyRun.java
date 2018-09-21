package com.luckystone.utils;

import java.util.concurrent.CountDownLatch;

public class ConcurrencyRun {

	/**
	 * 并发跑count次
	 * @param count 次数
	 * @param runt 
	 */
	public static void run(int count, final RunTemplate runt) {
		final CountDownLatch cdl = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						runt.proccess();
					} catch(Exception ex) {
						ex.printStackTrace();
					} finally{
						cdl.countDown();
					}
				}
			}).start();
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 并发跑count次
	 * @param count 次数
	 * @param runt 
	 */
	public static void run(int count, final RunProcessTemplate runt) {
		final CountDownLatch cdl = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						runt.beforeProcess(index);
						runt.proccess(index);
					} catch(Exception ex) {
						ex.printStackTrace();
					} finally{
						runt.afterProcess(index);
						cdl.countDown();
					}
				}
			}).start();
		}
		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static interface RunTemplate {
		
		/*处理中**/
		public void proccess();
	}
	
	public static interface RunProcessTemplate {
		
		/*处理中**/
		public void proccess(int index);
		
		/*处理之后*/
		public void afterProcess(int index);
		
		/*处理前*/
		public void beforeProcess(int index);
	}
}
