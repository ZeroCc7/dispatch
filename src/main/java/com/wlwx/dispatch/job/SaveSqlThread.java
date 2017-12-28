package com.wlwx.dispatch.job;


import com.wlwx.dispatch.entity.dispatch.SqlVo;
import com.wlwx.dispatch.service.DispatchService;
import com.wlwx.dispatch.service.impl.DispatchServiceImp;
import com.wlwx.dispatch.util.PublicFunctions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class SaveSqlThread extends Thread {
	private volatile static ArrayBlockingQueue<SqlVo> sqlVoQueue1 = new ArrayBlockingQueue<SqlVo>(200000);
	
	private static SaveSqlThread instance;
	private Thread submitThread;
	private DispatchService dao = new DispatchServiceImp();

	public synchronized static SaveSqlThread getInstance() {
		if (instance == null) {
			instance = new SaveSqlThread();
			instance.handleBatchUpdateQueueThread();
		}
		return instance;
	}

	/*
	 * 
	 * public boolean add(E e) 方法将抛出IllegalStateException异常，说明队列已满。 public
	 * boolean offer(E e) 方法则不会抛异常，只会返回boolean值，告诉你添加成功与否，队列已满，当然返回false。 public
	 * void put(E e) throws InterruptedException
	 * 方法则一直阻塞（即等待，直到线程池中有线程运行完毕，可以加入队列为止）。
	 */
	public boolean addEfdSmrptToQueue(SqlVo sqlVo) {
			boolean success = sqlVoQueue1.add(sqlVo);
			return success;
	}


	public void handleBatchUpdateQueueThread() {
		Runnable r = new Runnable() {
			public void run() {
				while (true) {
					try {
						long beginTime = System.currentTimeMillis();
						batchExcuteSql(sqlVoQueue1);
						try {
							long finishTime = System.currentTimeMillis();
							long executeTime = finishTime - beginTime;
							if (executeTime < 500) {
								// 如果执行时间小于0.5秒，说明没有大量操作，则sleep。否则，说明有大量操作，不需要sleep
								Thread.sleep(1000 - executeTime);
							}
						} catch (InterruptedException e) {
//							PublicConstants.tasklog.error("SaveSql调度线程中断等待", e);
							continue;
						}
					} catch (Exception ex) {
//						PublicConstants.tasklog.error("SaveSql调度线程出现异常", ex);
					}
				}
			}
		};
		submitThread = new Thread(r);
		submitThread.start();
	}

	public void batchExcuteSql( ArrayBlockingQueue<SqlVo> sqlVoQueue) {
		// 批量保存短信状任务号码状态
		List<SqlVo> sqlVoList = new ArrayList<SqlVo>();
		sqlVoQueue.drainTo(sqlVoList);
		if (!PublicFunctions.isBlankList(sqlVoList)) {
			for(SqlVo sqlVo : sqlVoList){
				dao.BatchExcuteSql(sqlVo);
			}
		}
	}

	
	
}
