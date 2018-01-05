package com.wlwx.dispatch.job;

import com.wlwx.dispatch.entity.dispatch.Smdown;
import com.wlwx.dispatch.service.DispatchService;
import com.wlwx.dispatch.util.PublicConstants;
import com.wlwx.dispatch.util.SpringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class SmsDispatchJob {
    private DispatchService dispatchService;
    PublicConstants p;
    private volatile boolean state;
    private DispatchThread dispatchThread;
    private SendSmsTask sendSmsTask;
    private SendSmsTask firstSendSmsTask;
    private Logger logger = LogManager.getLogger(SmsDispatchJob.class);


    public boolean isState() {
        return state;
    }

//    private static final SmsDispatchJob job = new SmsDispatchJob();
//
//    //静态工厂方法
//    public static SmsDispatchJob getInstance() {
//        return job;
//    }

    public SmsDispatchJob(){
        dispatchService = (DispatchService) SpringUtil.getBean("dispatchServiceImp");
        p = (PublicConstants) SpringUtil.getBean("publicConstants");
        sendSmsTask = new SendSmsTask(false);
        firstSendSmsTask = new SendSmsTask(true);
    }


    public boolean startDispatch() {
        if (!state) {
            logger.info("短信群发调度启动...");
            //第一次执行
            try {
                dispatchService.initTaskStatus();
            } catch (SQLException e1) {
//            PublicConstants.tasklog.info("初始化异常", e1);
                e1.printStackTrace();
                return false;
            }
            // 启动下行短信发送线程
            startDispatchThread();
        }
        state = true;
        return state;
    }

    public void shutdownDispatch() {
        state = false;
        if (dispatchThread != null && dispatchThread.isAlive()) {
            try {
                dispatchThread.interrupt();
                dispatchThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dispatchThread = null;
        }
        //TODO 监听端口停止


    }

    /**
     * 自定义的一个UncaughtExceptionHandler
     */
    class ErrHandler implements Thread.UncaughtExceptionHandler {
        /**
         * 这里可以做任何针对异常的处理,比如记录日志等等
         */
        public void uncaughtException(Thread a, Throwable e) {
            logger.info("线程" + a.getName() + "异常退出,Message:" + e);
            e.printStackTrace();
            // 重启异常退出的线程
            startDispatchThread();
        }
    }

    private void startDispatchThread() {
        if (dispatchThread == null) {
            dispatchThread = new DispatchThread();
        }
        ErrHandler handle = new ErrHandler();
        dispatchThread.setUncaughtExceptionHandler(handle);
        dispatchThread.start();
    }

    class DispatchThread extends Thread {
        public void run() {
            while (state) {
                int count = 0;
                int sendRate = p.getSendRate();
                try {
                    long starTime = System.currentTimeMillis();
                    List<Smdown> stList = dispatchService.getAllWaitSendSmsTask();
                    dispatchService.batchUpdateTaskStatus(stList);
                    logger.info("获取(size=" + stList.size() + ")任务耗时：" + (System.currentTimeMillis() - starTime) + " ms");
                    for (Smdown st : stList) {
                        if (st.getSendlevel() == 0) {
                            sendSmsTask.sendToSmdown(st);
                        } else {
                            firstSendSmsTask.sendToSmdown(st);
                        }
                        //计算总号码数
                        count += st.getSm_serialphones().split(",").length;
                    }
                } catch (Exception ex) {
                        logger.error("获取待发表异常", ex);
                    ex.printStackTrace();
                }
                try {
                    //根据发送速率控制发送速率
                    int sendTime = (int) (Math.floor(count / sendRate));
                    if (sendTime > 10) {
                        Thread.sleep(sendTime * 1000);
                    } else {
                        Thread.sleep(10000);
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    logger.error("任务调度线程sleep 异常",e);
                    e.printStackTrace();
                }
            }
        }
    }
}


