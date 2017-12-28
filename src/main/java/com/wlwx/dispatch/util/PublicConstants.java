package com.wlwx.dispatch.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 公共全局常量类
 * @author cheney
 *
 */
@Component
@ConfigurationProperties(prefix="dispatch") //application.yml中的myProps下的属性
public class PublicConstants {


	//用户业务主任务池参数

	private  int datCoreSize ;

	private  int datMaxSize;

	private  int datQueueSize;

	private  int maxSendRate;

	private  int custId;

	private  int firstSmsCustId;

	private  String custCode;

	private  String custPassword;

	private  String firstCustCode;

	private  String firstCustPassword;

	private  long reportSleepTime;

	private  long moSleepTime;

	private  int reportMoPort;

	private  String baseUrl;

	private  int sendRate;

	public int getDatCoreSize() {
		return datCoreSize;
	}

	public void setDatCoreSize(int datCoreSize) {
		this.datCoreSize = datCoreSize;
	}

	public int getDatMaxSize() {
		return datMaxSize;
	}

	public void setDatMaxSize(int datMaxSize) {
		this.datMaxSize = datMaxSize;
	}

	public int getDatQueueSize() {
		return datQueueSize;
	}

	public void setDatQueueSize(int datQueueSize) {
		this.datQueueSize = datQueueSize;
	}

	public int getMaxSendRate() {
		return maxSendRate;
	}

	public void setMaxSendRate(int maxSendRate) {
		this.maxSendRate = maxSendRate;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public int getFirstSmsCustId() {
		return firstSmsCustId;
	}

	public void setFirstSmsCustId(int firstSmsCustId) {
		this.firstSmsCustId = firstSmsCustId;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public String getCustPassword() {
		return custPassword;
	}

	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}

	public String getFirstCustCode() {
		return firstCustCode;
	}

	public void setFirstCustCode(String firstCustCode) {
		this.firstCustCode = firstCustCode;
	}

	public String getFirstCustPassword() {
		return firstCustPassword;
	}

	public void setFirstCustPassword(String firstCustPassword) {
		this.firstCustPassword = firstCustPassword;
	}

	public long getReportSleepTime() {
		return reportSleepTime;
	}

	public void setReportSleepTime(long reportSleepTime) {
		this.reportSleepTime = reportSleepTime;
	}

	public long getMoSleepTime() {
		return moSleepTime;
	}

	public void setMoSleepTime(long moSleepTime) {
		this.moSleepTime = moSleepTime;
	}

	public int getReportMoPort() {
		return reportMoPort;
	}

	public void setReportMoPort(int reportMoPort) {
		this.reportMoPort = reportMoPort;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getSendRate() {
		return sendRate;
	}

	public void setSendRate(int sendRate) {
		this.sendRate = sendRate;
	}
}

