package com.wlwx.dispatch.server;


import com.alibaba.fastjson.JSONObject;
import com.wlwx.dispatch.entity.dispatch.*;
import com.wlwx.dispatch.job.SaveMoThread;
import com.wlwx.dispatch.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class MoReportServerHandler extends IoHandlerAdapter {

    @Override
    public void sessionOpened(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 5);
    }

    private final static Logger logger = LogManager.getLogger(MoReportServerHandler.class);

    private RedisUtil redisUtil = (RedisUtil) SpringUtil.getBean("redisUtil");

    @Override
    public void messageReceived(IoSession session, Object message) {

        HttpResponseMessage response = new HttpResponseMessage();
        response.setContentType("application/json;charset=UTF-8");
        response.setResponseCode(HttpResponseMessage.HTTP_STATUS_SUCCESS);

        HttpRequestMessage reqMsg = (HttpRequestMessage) message;
        String path = reqMsg.getHeader("Context")[0];
        String content = reqMsg.getHeader("Content")[0];


        if (path.equalsIgnoreCase("mo")) {
            logger.info("接收到上行 content= " + content);
            String msgId = reqMsg.getParameter("msg_id");
            String sp_code = reqMsg.getParameter("sp_code");
            String src_mobile = reqMsg.getParameter("src_mobile");
            String msg_content =reqMsg.getParameter("msg_content");
            String recv_time = reqMsg.getParameter("recv_time");

            try {
                msg_content = URLDecoder.decode(msg_content,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("上行("+msg_content+")解析异常..",e);
                e.printStackTrace();
            }
            EfdSmup efdUp = new EfdSmup();
            efdUp.setFullcode(sp_code);
            efdUp.setSm_servicecode("");
            efdUp.setUpphone(src_mobile);
            efdUp.setUpcontent(msg_content);
            String recvT = recv_time;
            if(recv_time.contains(".")){
                recvT = recvT.substring(0, recvT.indexOf("."));
            }
            efdUp.setUptime(recvT);
            try {
                efdUp.setIsmgtype(MobilePrefix.getInstance().getProvider(src_mobile));
            } catch (Exception e) {
                logger.error("号码{"+src_mobile+")号段归属解析异常...",e);
                e.printStackTrace();
            }
            efdUp.setGatewayno(PublicFunctions.getMoMsgId()+"");
            SaveMoThread.getInstance().addEfdSmupToQueue(efdUp);
            logger.info("收到上行记录：" + JSONObject.toJSONString(efdUp));
        } else {
            List<ReportVo> reportList;
            try {
                reportList = parseStrToReportList(content);
            } catch (Exception e) {
                logger.error("状态报告解析响应异常 content= " + content, e);
                return;
            }
            if (reportList.size() == 0) {
                return;
            }
            List<EfdSmrpt> list = new ArrayList<EfdSmrpt>();
            for (ReportVo rvo : reportList) {
                int rptStatus = 1;
                if (rvo.getReport_status().equals("DELIVRD")) {
                    rptStatus = 0;
                }
                EfdSmrpt efd = new EfdSmrpt();
                efd.setMessageid(rvo.getMsgid());
                efd.setRpt_status(rptStatus);
                efd.setRpt_time(rvo.getRecv_time());
                efd.setSm_recvphone(rvo.getMobile());
                efd.setSm_batchno(rvo.getUid());
                efd.setRpt_code(rvo.getReport_status());
                list.add(efd);
                logger.info("接收到状态报告:" + JSONObject.toJSONString(rvo));
            }
            SqlVo sqlVo = PublicFunctions.getUpdateReptEfdSmrptSql(list);
            redisUtil.rpushSql(sqlVo);
            if (response != null) {
                response.appendBody("ok");
                session.write(response);
            }
        }
    }

    /**
     * 解析上行
     *
     * @param content
     * @return
     */
    private List<MoVo> parseStrToMoList(String content) {
        // TODO Auto-generated method stub



        return null;
    }

    /**
     * 解析状态报告
     *
     * @param content
     * @return report=0904d5125d6d44000002,手机号码,DELIVRD, 2015-03-18 10:03:12,140000000^0904d5125d6d44000004,手机号码,DELIVRD, 2015-03-18 10:03:12,140000000^0904d5125d6d44000006,手机号码,DELIVRD, 2015-03-18 10:03:12,140000000^
     */
    private List<ReportVo> parseStrToReportList(String content) {
        content = content.substring("report=".length());
        String[] reportStr = content.split("\\^");
        List<ReportVo> reportList = new ArrayList<ReportVo>();
        for (String reports : reportStr) {
            ReportVo reportVo = new ReportVo();
            String[] param = reports.split(",");
            reportVo.setMsgid(param[0]);
            reportVo.setMobile(param[1]);
            if (null == param[2] || "".equals(param[2])) {
                reportVo.setReport_status("FAIL");
            } else {
                reportVo.setReport_status(param[2]);
            }
            reportVo.setRecv_time(param[3]);
            if (param.length >= 5)
                reportVo.setUid(param[4]);
            reportList.add(reportVo);
        }
        return reportList;
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        session.close(true);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
    }


}
