package com.wlwx.dispatch.job;

import com.wlwx.dispatch.entity.dispatch.EfdSmup;
import com.wlwx.dispatch.server.MoReportServerHandler;
import com.wlwx.dispatch.service.DispatchService;
import com.wlwx.dispatch.util.PublicFunctions;
import com.wlwx.dispatch.util.SpringUtil;
import com.wlwx.dispatch.util.Statistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SaveMoThread extends Thread {
	private final static Logger logger = LogManager.getLogger(SaveMoThread.class);
	private volatile static ArrayBlockingQueue<EfdSmup> efdSmupQueue = new ArrayBlockingQueue<EfdSmup>(200000);
	private DispatchService dao = (DispatchService) SpringUtil.getBean("dispatchServiceImp");
	private static SaveMoThread instance;
	private Thread submitThread;
	public synchronized static SaveMoThread getInstance() {
		if (instance == null) {
			instance = new SaveMoThread();
			instance.handleBatchSaveQueueThread();
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
	public boolean addEfdSmupToQueue(EfdSmup efdSmup) {
		boolean success = efdSmupQueue.add(efdSmup);
		return success;
	}


	public void handleBatchSaveQueueThread() {
		Runnable r = new Runnable() {
			public void run() {
				long starTime = new Date().getTime();
				while (true) {
					try {
						long beginTime = System.currentTimeMillis();
						batchSaveMobile(efdSmupQueue);
						if(beginTime-starTime > 10000){
							starTime = beginTime;
						}
						
						try {
							long finishTime = System.currentTimeMillis();
							long executeTime = finishTime - beginTime;
							if (executeTime < 500) {
								// 如果执行时间小于0.5秒，说明没有大量操作，则sleep。否则，说明有大量操作，不需要sleep
								Thread.sleep(1000 - executeTime);
							}
						} catch (InterruptedException e) {
							logger.error("批量保存SaveMoThread调度线程中断等待", e);
							Statistics.ExceptionNum++;
							continue;
						}
					} catch (Exception ex) {
						Statistics.ExceptionNum++;
						logger.error("批量保存SaveMoThread调度线程出现异常", ex);
					}
				}
			}
		};
		submitThread = new Thread(r);
		submitThread.start();
	}

	@SuppressWarnings("unchecked")
	public void batchSaveMobile(ArrayBlockingQueue<EfdSmup> efdQueue) {
		// 批量保存短信状任务号码状态
		List<EfdSmup> efdList = new ArrayList<EfdSmup>();
		efdQueue.drainTo(efdList);
		if (!PublicFunctions.isBlankList(efdList)) {
			if (efdList.size() > 500) {
				// list超过1000,进行分割成小list
				List<EfdSmup>[] efdSubLists = PublicFunctions.splitList(efdList, 500);
				for (List<EfdSmup> efdSmup : efdSubLists) {
					dao.batchInsertEfdSmup(efdSmup);
				}
			} else {
				dao.batchInsertEfdSmup(efdList);
			}
		}
	}
	
}
