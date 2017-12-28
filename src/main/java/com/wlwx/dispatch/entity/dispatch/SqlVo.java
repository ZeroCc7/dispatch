package com.wlwx.dispatch.entity.dispatch;

import java.util.List;

public class SqlVo {
	private int type; // 1 插入 2 修改
	private String sqlStr;
	private List<EfdSmrpt> dateList;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSqlStr() {
		return sqlStr;
	}
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	public List<EfdSmrpt> getDateList() {
		return dateList;
	}
	public void setDateList(List<EfdSmrpt> dateList) {
		this.dateList = dateList;
	}
	
}
