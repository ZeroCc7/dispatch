package com.wlwx.dispatch.job;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wlwx.dispatch.entity.dispatch.*;
import com.wlwx.dispatch.service.DispatchService;
import com.wlwx.dispatch.service.impl.DispatchServiceImp;
import com.wlwx.dispatch.util.PublicConstants;
import com.wlwx.dispatch.util.PublicFunctions;
import com.wlwx.dispatch.util.UMD5;

public class SendSmsTaskThread implements Runnable {
	private String cost_code = "";
	private String passWord = "";
	private String smsSvcUrl=null;
	private DispatchService dataAccess = new DispatchServiceImp();
	private Smdown smdown;
	private boolean first;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private PublicConstants publicConstants;


	public SendSmsTaskThread(Smdown smdown, boolean first){
		this.smdown = smdown;
		this.first = first;
		publicConstants = new PublicConstants();
	}
	public void run() {
		long starTime = System.currentTimeMillis();
		smsSvcUrl = "http://"+publicConstants.getBaseUrl();
		int cust_id =publicConstants.getCustId();
		if(first){
			cust_id =publicConstants.getFirstSmsCustId();
		}
		Customer customer = dataAccess.getCusomerById(cust_id);
		if(customer==null || customer.getStatus().equals("0")){
//				PublicConstants.tasklog.info("custome 不存在或停用 cust_id = "+cust_id);
			return;
		}
		cost_code = customer.getCust_code();
		passWord = customer.getPasswd();
		List<EfdSmrpt> efdSmrList = null;
		try {
			efdSmrList = PublicFunctions.getEfdSmrptListBySmDown(smdown);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
//			PublicConstants.tasklog.info("downId:"+smdown.getDownid()+ "解析任务异常....",e1);
			e1.printStackTrace();
			return;
		}
		SqlVo sqlVo = PublicFunctions.getInsertEfdSmrptListSql(efdSmrList);
		SaveSqlThread.getInstance().addEfdSmrptToQueue(sqlVo);
		StringBuffer phones = new StringBuffer();
		int num = 0;
		String[] descS = smdown.getSm_serialphones().split(",");
		for(String desc : descS){
			String[] d =  desc.split(":");
			String mobile =d[1];
			phones.append(",");
			phones.append(mobile);
			num++;
		}
		phones.substring(1);
		SendSms2Http(efdSmrList,smdown,phones.toString());
		try {
			dataAccess.updateTaskStatusToEnd(smdown);
		} catch (SQLException e) {
//			PublicConstants.tasklog.info("downId:"+smdown.getDownid()+ ",修改任务完成异常....",e);
			e.printStackTrace();
		}
//		PublicConstants.tasklog.info("downId:"+smdown.getDownid()+ ",phoneSize=" +num+" ,发送完成...耗时:"+(System.currentTimeMillis()- starTime)+" ms");
		try {
			long finishTime = System.currentTimeMillis();
			long executeTime = finishTime - starTime;
			if (num == 150 && 950 - executeTime > 0) {
				// 如果执行时间小于0.5秒，说明没有大量操作，则sleep。否则，说明有大量操作，不需要sleep
				Thread.sleep(950 - executeTime);
			} else {
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
//			PublicConstants.tasklog.error("SaveSql调度线程中断等待", e);
			return;
		}
	
	}
	
	/**
	 * 发送至接口。
	 */
	private void SendSms2Http(List<EfdSmrpt> submitEfdList ,Smdown sm,String mobiles) {
		String sendTimeStr = sdf.format(new Date()); // 提交时间
		String resultMsg = null;
		String postData = null;
		try {
			String sign= UMD5.sign(sm.getSm_content(), passWord, "utf-8");
			Map<String,Object> submit = new HashMap<String,Object>();
			submit.put("cust_code", cost_code);
			submit.put("sp_code", sm.getSm_servicecode() == null ? "" : sm.getSm_servicecode());
			submit.put("content", sm.getSm_content());
			submit.put("destMobiles", mobiles);
			submit.put("sign", sign);
			submit.put("uid", submitEfdList.get(0).getSm_serialno());
			//是否要状态报告字段
			submit.put("need_report", "YES");
			postData = JSONObject.toJSONString(submit);
			resultMsg = PublicFunctions.postHttpRequest(smsSvcUrl+"/sendSms", postData);
		} catch (IOException e) {
//			PublicConstants.tasklog.info("downId:"+sm.getDownid()+",提交接口异常："+ postData,e);
			String subFailTimeStr = sdf.format(new Date()); // 提交时间
			for(EfdSmrpt efrpt : submitEfdList){
				efrpt.setSm_sendtime(sendTimeStr);
				efrpt.setSendtime(sendTimeStr);
				efrpt.setRes_status(1);
				efrpt.setRes_time(subFailTimeStr);
				efrpt.setRpt_status(1);
				efrpt.setRpt_time(subFailTimeStr);
				efrpt.setRpt_code("SubError");
			}
			SqlVo sqlVo = PublicFunctions.getUpdateEfdSmrptListSql(submitEfdList);
			SaveSqlThread.getInstance().addEfdSmrptToQueue(sqlVo);
			return;
		}
		SubmitRepVo submitRepVo = JSON.parseObject(resultMsg, SubmitRepVo.class);
		String sendRepTimeStr = sdf.format(new Date());
		int excessFlowNum = 0;
		String excessMobile = "";
		
		
		if (submitRepVo.getStatus().equals("success")) {
			List<ResultVo> resultList = submitRepVo.getResult();
			List<EfdSmrpt> saveEfdSmrptList = new ArrayList<EfdSmrpt>();
			for(ResultVo resultVo : resultList){
//				PublicConstants.tasklog.info("downId:"+sm.getDownid()+",msgId="+resultVo.getMsgid()+ ",mobile="+ resultVo.getMobile() +",status="+resultVo.getCode());
				if (resultVo.getCode().equals("0")) {
					String submitSuccessTime = sdf.format(new Date());
					for(EfdSmrpt efrpt : submitEfdList){
						if(resultVo.getMobile().equals(efrpt.getSm_recvphone()) && efrpt.getMessageid().equals(String.valueOf(efrpt.getGatewayno()))){
							efrpt.setMessageid(resultVo.getMsgid());
							efrpt.setRes_status(0);
							efrpt.setRpt_status(2);
							efrpt.setRes_time(submitSuccessTime);
							efrpt.setRpt_time(submitSuccessTime);
							efrpt.setSm_sendtime(sendTimeStr);
							efrpt.setSendtime(sendTimeStr);
							efrpt.setRpt_code("0");
							saveEfdSmrptList.add(efrpt);
						}
					}
				}else if (resultVo.getCode().equals("8")) {
					//超流操作
					excessFlowNum++;
					excessMobile = excessMobile + "," + resultVo.getMobile();
					
				} else {
					for(EfdSmrpt efrpt : submitEfdList){
						if(resultVo.getMobile().equals(efrpt.getSm_recvphone()) && efrpt.getMessageid().equals(String.valueOf(efrpt.getGatewayno()))){
							efrpt.setMessageid(resultVo.getMsgid());
							efrpt.setRes_status(1);
							efrpt.setRpt_status(1);
							efrpt.setRes_time(sendRepTimeStr);
							efrpt.setRpt_time(sendRepTimeStr);
							efrpt.setSm_sendtime(sendTimeStr);
							efrpt.setSendtime(sendTimeStr);
							efrpt.setRpt_code(resultVo.getCode());
							saveEfdSmrptList.add(efrpt);
						}
					}
				}
			}
			SqlVo sqlVo = PublicFunctions.getUpdateEfdSmrptListSql(saveEfdSmrptList);
			SaveSqlThread.getInstance().addEfdSmrptToQueue(sqlVo);
			if(excessFlowNum>0){
				//超流号码再次提交
				if(excessMobile.startsWith(",")){
					excessMobile = excessMobile.substring(1);
				}
				SendSms2Http(submitEfdList,sm, excessMobile);
//				PublicConstants.tasklog.info("downId:"+sm.getDownid()+", 超流(phone = "+excessMobile+")重发...");
			}
			
		} else {
//			try {
////				PublicConstants.tasklog.info("downId:"+sm.getDownid()+", 号码发送失败 resultMsg="+URLDecoder.decode(resultMsg, "UTF-8"));
//			} catch (UnsupportedEncodingException e) {
////				PublicConstants.tasklog.info("downId:"+sm.getDownid()+", 响应解析失败"+ resultMsg);
//				e.printStackTrace();
//			}
			String subFailTimeStr = sdf.format(new Date()); // 提交时间
			for(EfdSmrpt efrpt : submitEfdList){
				efrpt.setRes_status(1);
				efrpt.setRpt_status(1);
				efrpt.setRes_time(subFailTimeStr);
				efrpt.setRpt_time(subFailTimeStr);
				efrpt.setSm_sendtime(sendTimeStr);
				efrpt.setSendtime(sendTimeStr);
				efrpt.setRpt_code(submitRepVo.getRespCode());
			}
			SqlVo sqlVo = PublicFunctions.getUpdateEfdSmrptListSql(submitEfdList);
			SaveSqlThread.getInstance().addEfdSmrptToQueue(sqlVo);
		}
	}
}
