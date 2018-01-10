package com.wlwx.dispatch.job;

import com.wlwx.dispatch.entity.dispatch.Smdown;
import com.wlwx.dispatch.util.PublicConstants;
import com.wlwx.dispatch.util.SpringUtil;
import com.wlwx.dispatch.util.Statistics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class SendSmsTask {
	private ArrayBlockingQueue<Smdown> msgQueue;
	private ThreadPoolExecutor taskExecutor;
	private SendSmsThread sendSmsThread;
	private PublicConstants publicConstants;
	private boolean isFirst;
	private Logger logger = LogManager.getLogger(SendSmsTask.class);


	public SendSmsTask(boolean isFirst){
		msgQueue = new ArrayBlockingQueue<Smdown>(10000);
		publicConstants =(PublicConstants) SpringUtil.getBean("publicConstants");
		this.isFirst = isFirst;
		taskExecutor = new ThreadPoolExecutor(publicConstants.getDatCoreSize(), publicConstants.getDatMaxSize(),
				30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(publicConstants.getDatQueueSize()),
				new ThreadPoolExecutor.AbortPolicy());
		startSendSmsThread();

	}
	
	public void sendToSmdown(Smdown st) {
		try {
			msgQueue.put(st);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void startSendSmsThread() {
		if (sendSmsThread == null) {
			sendSmsThread = new SendSmsThread();
		}
		ErrHandler handle = new ErrHandler();
		sendSmsThread.setUncaughtExceptionHandler(handle);
		sendSmsThread.start();
	}
	
	class SendSmsThread extends Thread {
		public void run() {
//			PublicConstants.tasklog.info("群发入队线程启动....");
			while (true) {
				try {
					Thread.sleep(1000);
					List<Smdown> smList = new ArrayList<Smdown>();
					msgQueue.drainTo(smList);
					if(smList.size() == 0){
						continue;
					}
				
					for(Smdown sm : smList){
						taskExecutor.submit(new SendSmsTaskThread(sm,isFirst));
					}
				} catch (Exception ex) {
					logger.error("发送线程出现异常：", ex);
					Statistics.ExceptionNum++;
					continue;
				}
				logger.info(Thread.currentThread().getName()+" is working......");
			}
		}
	}
	/**
	 * 自定义的一个UncaughtExceptionHandler
	 */
	class ErrHandler implements UncaughtExceptionHandler {
		/**
		 * 这里可以做任何针对异常的处理,比如记录日志等等
		 */
		public void uncaughtException(Thread a, Throwable e) {
			logger.error("线程" + a.getName() + "异常退出,Message:", e);
			// 重启异常退出的线程
			startSendSmsThread();
		}
	}
	
}
