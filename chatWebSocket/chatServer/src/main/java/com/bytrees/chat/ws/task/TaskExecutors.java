package com.bytrees.chat.ws.task;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 执行任务的线程池
 *
 */
public class TaskExecutors {

	
	/**
	 * 业务逻辑线程池
	 */
	private ThreadPoolExecutor threadPoolExecutor;

	public TaskExecutors() {
		threadPoolExecutor = new ThreadPoolExecutor(2, //核心线程池大小
			5,                  //最大线程池大小
			20L,                //线程最大空闲时间
			TimeUnit.SECONDS,  //时间单位
			new SynchronousQueue<Runnable>(false),         //线程等待队列
			new ThreadFactoryImpl("Chat-Server-Business")   //线程创建工厂
		);
	}

	/**
	 * 执行任务
	 */
	public void execute(Runnable r) {
		threadPoolExecutor.execute(r);
	}
}
