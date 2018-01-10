package com.wlwx.dispatch.job;


import com.wlwx.dispatch.entity.dispatch.SqlVo;
import com.wlwx.dispatch.service.DispatchService;
import com.wlwx.dispatch.service.impl.DispatchServiceImp;
import com.wlwx.dispatch.util.PublicFunctions;
import com.wlwx.dispatch.util.RedisUtil;
import com.wlwx.dispatch.util.SpringUtil;
import com.wlwx.dispatch.util.Statistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class SaveSqlThread extends Thread {
	private static SaveSqlThread instance;
	private Thread submitThread;
	private DispatchService dispatchService = (DispatchService) SpringUtil.getBean("dispatchServiceImp");
	private Logger logger = LogManager.getLogger(SaveSqlThread.class);
	private static RedisUtil redisUtil;
	public synchronized static SaveSqlThread getInstance() {
		if (instance == null) {
			instance = new SaveSqlThread();
			instance.handleBatchUpdateQueueThread();
			redisUtil = (RedisUtil) SpringUtil.getBean("redisUtil");
		}
		return instance;
	}


	public void handleBatchUpdateQueueThread() {
		Runnable r = new Runnable() {
			public void run() {
				while (true) {
					try {
						long beginTime = System.currentTimeMillis();
						SqlVo sqlVo =redisUtil.lpopSql();
						if(sqlVo == null){
							continue;
						}
						dispatchService.BatchExcuteSql(sqlVo);
						long finishTime = System.currentTimeMillis();
						long executeTime = finishTime - beginTime;
//						if (executeTime < 500) {
//							// 如果执行时间小于0.5秒，说明没有大量操作，则sleep。否则，说明有大量操作，不需要sleep
//							Thread.sleep(1000 - executeTime);
//						}
					} catch (Exception ex) {
						logger.error("SaveSql线程出现异常", ex);
						Statistics.ExceptionNum++;
						continue;
					}
				}
			}
		};
		submitThread = new Thread(r);
		submitThread.start();
	}
}
