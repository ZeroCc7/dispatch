package com.wlwx.dispatch.entity;

import java.math.BigDecimal;


public class SmsTask {
    private int cust_id;// number(8) y 客户ID
    private long task_id;// number(10) n 任务ID
    private String task_name;// varchar2(30) N 任务名称
    private String sms_content;// varchar2(600) N 短信内容
    private String submit_time;// date y 提交时间
    private String begin_time;// date y 开始发送时间
    private String end_time;// date y 结束发送时间
    private String status;// char(1) n "状态
    private String status_note;// varchar2(200) Y 状态说明
    private long total_num;// number(8) y 总条数
    private long sended_num;// number(8) Y 已经发送条数


    private int divided_num;// number(2) Y 计费条数
    private BigDecimal charge_fee;// number(12,4) N 单条费率
    private BigDecimal charge_amount;// number(12,4) Y 已扣费
    private String create_time;// date n 任务创建时间
    private String create_by;// varchar2(20) N 创建人
    private int invalid_num;// number(8) N 无效号码数量
    private int black_num;// number(8) n 黑名单号码数量
    private String file_name;// varchar2(100) Y 号码文件名
    private String finish_time;//任务时间完成时间
    private String dest_type;
    private String dest_mobile;
    private String ext_code;
    private int priority;
    private int create_user;
    private Integer agent_id;
    private Integer agent_acct_id;
    private long send_task_id;// number(10) n 任务ID 页面重复 无法提交，添加该ID
    private int send_task_num;
    private int success_num;
    private int fail_num;
    private String task_ids;
    /**
     * 子任务个数
     */
    private int taskNum;
    private String statuss;
    private String task_type;// 任务类型

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getSms_content() {
        return sms_content;
    }

    public void setSms_content(String sms_content) {
        this.sms_content = sms_content;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_note() {
        return status_note;
    }

    public void setStatus_note(String status_note) {
        this.status_note = status_note;
    }

    public long getTotal_num() {
        return total_num;
    }

    public void setTotal_num(long total_num) {
        this.total_num = total_num;
    }

    public long getSended_num() {
        return sended_num;
    }

    public void setSended_num(long sended_num) {
        this.sended_num = sended_num;
    }

    public int getDivided_num() {
        return divided_num;
    }

    public void setDivided_num(int divided_num) {
        this.divided_num = divided_num;
    }

    public BigDecimal getCharge_fee() {
        return charge_fee;
    }

    public void setCharge_fee(BigDecimal charge_fee) {
        this.charge_fee = charge_fee;
    }

    public BigDecimal getCharge_amount() {
        return charge_amount;
    }

    public void setCharge_amount(BigDecimal charge_amount) {
        this.charge_amount = charge_amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public int getInvalid_num() {
        return invalid_num;
    }

    public void setInvalid_num(int invalid_num) {
        this.invalid_num = invalid_num;
    }

    public int getBlack_num() {
        return black_num;
    }

    public void setBlack_num(int black_num) {
        this.black_num = black_num;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getDest_type() {
        return dest_type;
    }

    public void setDest_type(String dest_type) {
        this.dest_type = dest_type;
    }

    public String getDest_mobile() {
        return dest_mobile;
    }

    public void setDest_mobile(String dest_mobile) {
        this.dest_mobile = dest_mobile;
    }

    public String getExt_code() {
        return ext_code;
    }

    public void setExt_code(String ext_code) {
        this.ext_code = ext_code;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCreate_user() {
        return create_user;
    }

    public void setCreate_user(int create_user) {
        this.create_user = create_user;
    }

    public Integer getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(Integer agent_id) {
        this.agent_id = agent_id;
    }

    public Integer getAgent_acct_id() {
        return agent_acct_id;
    }

    public void setAgent_acct_id(Integer agent_acct_id) {
        this.agent_acct_id = agent_acct_id;
    }

    public long getSend_task_id() {
        return send_task_id;
    }

    public void setSend_task_id(long send_task_id) {
        this.send_task_id = send_task_id;
    }

    public int getSend_task_num() {
        return send_task_num;
    }

    public void setSend_task_num(int send_task_num) {
        this.send_task_num = send_task_num;
    }

    public int getSuccess_num() {
        return success_num;
    }

    public void setSuccess_num(int success_num) {
        this.success_num = success_num;
    }

    public int getFail_num() {
        return fail_num;
    }

    public void setFail_num(int fail_num) {
        this.fail_num = fail_num;
    }

    public String getTask_ids() {
        return task_ids;
    }

    public void setTask_ids(String task_ids) {
        this.task_ids = task_ids;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }
}
