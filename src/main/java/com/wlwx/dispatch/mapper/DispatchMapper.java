package com.wlwx.dispatch.mapper;

import com.wlwx.dispatch.entity.dispatch.Customer;
import com.wlwx.dispatch.entity.dispatch.EfdSmrpt;
import com.wlwx.dispatch.entity.dispatch.ProviderMobilePrefix;
import com.wlwx.dispatch.entity.dispatch.Smdown;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DispatchMapper {
    /**
     * 获取所有待发送任务
     * @return
     */
    List<Smdown> getAllWaitSendSmsTask();

    void initTaskStatus();

    void updateTaskStatusToEnd(Smdown sm);

    List<ProviderMobilePrefix> getProviderMobileList();

    ProviderMobilePrefix getProviderIdByMobile(String mobile);

    Customer getCusomerById(int cust_id);

    void insertEfdSmrpt(EfdSmrpt efdSmrpt);

    void updateSubmitEfdRpt(EfdSmrpt efdSmrpt);

    void updateReportEfdRpt(EfdSmrpt efdSmrpt);

    void updateSmdownStatus(Smdown smdown);
}
