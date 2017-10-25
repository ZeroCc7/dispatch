package com.wlwx.dispatch.mapper;

import com.wlwx.dispatch.entity.SmsTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SmsTaskMapper {
    /**
     * 获取当天任务
     * @return
     */
    List<SmsTask> getTodaySmsTasks();
}
