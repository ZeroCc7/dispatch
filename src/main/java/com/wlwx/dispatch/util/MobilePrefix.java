package com.wlwx.dispatch.util;


import javax.xml.bind.annotation.XmlRootElement;

import com.wlwx.dispatch.entity.dispatch.ProviderMobilePrefix;
import com.wlwx.dispatch.service.DispatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class MobilePrefix {
	private DispatchService dispatchService = (DispatchService) SpringUtil.getBean("dispatchServiceImp");
	private Logger logger= LogManager.getLogger(MobilePrefix.class);
	public static List<ProviderMobilePrefix> providerMobileList = null;
	private static MobilePrefix instance;

	public synchronized static MobilePrefix getInstance() {
		if (instance == null) {
			instance = new MobilePrefix();
			instance.intProviderMobileList();
		}
		return instance;
	}
	

	/**
	 * 获取运营商号段（缓存）
	 * 
	 * @return
	 */
	public void intProviderMobileList() {
		if (providerMobileList == null) {
			// 第一次加载
			providerMobileList = new ArrayList<ProviderMobilePrefix>();
			// 批量执行器
			try {
				providerMobileList = dispatchService.getProviderMobileList();
			} catch (Exception e) {
				logger.error("获取号码归属异常...",e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析号段归属
	 */

	public int getProvider(String mobile) throws Exception {
		int provider_id = 9;
		for (ProviderMobilePrefix pmpp : providerMobileList) {
			if (mobile.startsWith(pmpp.getMobile_prefix())) {
				provider_id = pmpp.getProvider_id();
			}
		}
		if (provider_id == 9) {
			ProviderMobilePrefix pfx = dispatchService.getProviderIdByMobile(mobile);
			if(pfx!=null){
				providerMobileList.add(pfx);
				provider_id = pfx.getProvider_id();
				
			}
		}
		switch (provider_id) {
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 2;
		default:
			return 9;
		}
	}

}
