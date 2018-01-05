package com.wlwx.dispatch.mapper.SmsMapper;

import com.wlwx.dispatch.entity.dispatch.Customer;
import com.wlwx.dispatch.entity.dispatch.EfdSmrpt;
import com.wlwx.dispatch.entity.dispatch.ProviderMobilePrefix;
import com.wlwx.dispatch.entity.dispatch.Smdown;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DispatchMapper {
    List<ProviderMobilePrefix> getProviderMobileList();

    ProviderMobilePrefix getProviderIdByMobile(String mobile);

    Customer getCusomerById(int cust_id);
}
