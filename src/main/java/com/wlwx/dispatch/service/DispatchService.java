package com.wlwx.dispatch.service;

import com.wlwx.dispatch.entity.dispatch.*;

import java.sql.SQLException;
import java.util.List;

/**
 * 调度service
 */
public interface DispatchService {

    /**
     * 获取所有待发送任务
     */
    List<Smdown> getAllWaitSendSmsTask() throws SQLException;


    /**
     * 初始化
     */
    void initTaskStatus() throws SQLException;

    /**
     * 更改任务状态结束
     */
    void updateTaskStatusToEnd(Smdown sm) throws SQLException;


    /**
     * 获取号段归属表
     */
    List<ProviderMobilePrefix> getProviderMobileList() throws Exception;

    /**
     * 根据号码获取归属
     */
    ProviderMobilePrefix getProviderIdByMobile(String mobile) throws Exception;

    /**
     * 批量执行SQL
     */
    void BatchExcuteSql(SqlVo sqlVo);

    /**
     * 批量更新任务状态
     */
    void batchUpdateTaskStatus(List<Smdown> stList) throws SQLException;

    /**
     * 查询客户信息
     */
    Customer getCusomerById(int cust_id)throws SQLException;

    /**
     * 批量保存上行
     * @param efdSmup
     */
    void batchInsertEfdSmup(List<EfdSmup> efdSmup);
}
