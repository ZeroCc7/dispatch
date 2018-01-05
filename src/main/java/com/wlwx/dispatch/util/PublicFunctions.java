package com.wlwx.dispatch.util;

import com.wlwx.dispatch.entity.dispatch.EfdSmrpt;
import com.wlwx.dispatch.entity.dispatch.Smdown;
import com.wlwx.dispatch.entity.dispatch.SqlVo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class PublicFunctions {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 判断list 是否为空
	 */
	public static <T> boolean isBlankList(List<T> list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}
	
	/**
	 * 拆分list
	 */
	@SuppressWarnings("rawtypes")
	public static List[] splitList(List list, int pageSize) {   
	    int total = list.size();   
	    int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;   
	    List[] result = new List[pageCount];   
	    for(int i = 0; i < pageCount; i++) {   
	        int start = i * pageSize;   
	        int end = start + pageSize > total ? total : start + pageSize;   
	        List subList = list.subList(start, end);   
	        result[i] = subList;   
	    }   
	    return result;   
	}

	
	// msgId 产生方法.
	private static int sequence = 0;

	public synchronized static long getMsgId() {
		sequence++;
		if (sequence >= 65536) {
			sequence = 1;
		}
		Calendar now = Calendar.getInstance();
		int mon = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		int ismgId = 59112;
		long msgId = mon;
		msgId = (msgId << 5) | day;
		msgId = (msgId << 5) | hour;
		msgId = (msgId << 6) | min;
		msgId = (msgId << 6) | sec;
		msgId = (msgId << 18) | ismgId;
		msgId = (msgId << 16) | sequence;
		return msgId;
	}

	
	public static String postHttpRequest(String url, String postData)
			throws IOException {
		String sessionId = UUID.randomUUID().toString().replace("-", "");

		URL myurl = new URL(url);
		URLConnection urlc = myurl.openConnection();
		urlc.setReadTimeout(1000 * 300);
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setAllowUserInteraction(false);

		DataOutputStream server = new DataOutputStream(urlc.getOutputStream());
		
//		PublicConstants.tasklog.info("SessionID:"+sessionId+", 发送数据=" + postData);

		server.write(postData.getBytes("utf-8"));
		server.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				urlc.getInputStream(), "utf-8"));
		String resXml = "", s = "";
		while ((s = in.readLine()) != null)
			resXml = resXml + s;
		in.close();
//		PublicConstants.tasklog.info("SessionID:"+sessionId+",接收数据=" + resXml);
		return resXml;
	}
	
	/**
     * 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @return
     */
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }
    
    /**
     * 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
            int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     * 
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }
    
    public synchronized static long getMoMsgId() {
		sequence++;
		if (sequence >= 65536) {
			sequence = 1;
		}
		Calendar now = Calendar.getInstance();
		int mon = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);
		int ismgId = 59112;
		long msgId = mon;
		msgId = (msgId << 5) | day;
		msgId = (msgId << 5) | hour;
		msgId = (msgId << 6) | min;
		msgId = (msgId << 6) | sec;
		msgId = (msgId << 1) | ismgId;
		msgId = (msgId << 1) | sequence;
		return msgId;
	}
    
	public static void main(String[] args) {
		String x = "";

		for(int i=0;i<10;i++){
			x+="," + getMoMsgId();
		}
		String y = "";
		for(int i=0;i<10;i++){
			y+="," + getMoMsgId();
		}
		System.out.println(y);

	}

	public static List<EfdSmrpt> getEfdSmrptListBySmDown(Smdown smdown) throws Exception{
		List<EfdSmrpt> efdrptList = new ArrayList<EfdSmrpt>();
//		PublicConstants.tasklog.info("获取到任务：" + JSONObject.toJSONString(smdown));
		List<EfdSmrpt> smOList = new ArrayList<EfdSmrpt>();
		String sendTimeStr = sdf.format(new Date());
		String[] descS = smdown.getSm_serialphones().split(",");
		for(String desc : descS){
			long msgId = getMsgId();
			String[] d =  desc.split(":");
			String uid =d[0];
			String mobile =d[1];
			EfdSmrpt efdSmrpt = new EfdSmrpt(smdown);
			efdSmrpt.setSm_recvphone(mobile);
			efdSmrpt.setSm_sendtime(sendTimeStr);
			efdSmrpt.setGatewayno(msgId);
			efdSmrpt.setMessageid(String.valueOf(msgId));
			efdSmrpt.setSendtime(sendTimeStr);
			efdSmrpt.setRes_status(0);
			efdSmrpt.setRes_time(sdf.format(new Date()));
			efdSmrpt.setRpt_status(2);
			efdSmrpt.setRpt_time(sdf.format(new Date()));
			efdSmrpt.setRpt_code("0");
			efdSmrpt.setPknumber(smdown.getPknumber());
			efdSmrpt.setPktotal(smdown.getPktotal());
			efdSmrpt.setSm_serialno(Long.parseLong(uid));
			if(smdown.getSm_content().length()>70){
				//是长短信
				String sm_content = smdown.getSm_content();
				List<String> sm_contentS = PublicFunctions.getStrList(sm_content,67);
				smdown.setPktotal(sm_contentS.size());
				int index = 1;
				for(String msg : sm_contentS){
					EfdSmrpt efdSmrpt2  = null;
					efdSmrpt2 = efdSmrpt.clone();
					efdSmrpt2.setSm_content("-");
					efdSmrpt2.setPknumber(index);
					efdSmrpt2.setPktotal(sm_contentS.size());
					efdrptList.add(efdSmrpt2);
					smOList.add(efdSmrpt2);
					index++;
				}
			}else{
				efdSmrpt.setSm_content("-");
				efdrptList.add(efdSmrpt);
				smOList.add(efdSmrpt);
			}
		}
		return efdrptList;
	}

	public static SqlVo getInsertEfdSmrptListSql(List<EfdSmrpt> efdSmrptList) {
		SqlVo sqlVo = new SqlVo();
		String sql = "";
		sqlVo.setType(1);
		sqlVo.setSqlStr(sql);
		sqlVo.setDateList(efdSmrptList);
		return sqlVo;
	}
	
	public static SqlVo getUpdateEfdSmrptListSql(List<EfdSmrpt> efdSmrptList) {
		SqlVo sqlVo = new SqlVo();
		String sql = "";
		sqlVo.setType(2);
		sqlVo.setSqlStr(sql);
		sqlVo.setDateList(efdSmrptList);
		return sqlVo;
	}
	
	public static SqlVo getUpdateReptEfdSmrptSql(List<EfdSmrpt> efdSmrptList) {
		SqlVo sqlVo = new SqlVo();
		String sql = "";
		sqlVo.setType(3);
		sqlVo.setSqlStr(sql);
		sqlVo.setDateList(efdSmrptList);
		return sqlVo;
	}
    
}
