package com.wlwx.dispatch.mapper.SmsMapper;

import com.wlwx.dispatch.entity.DayNum;
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

    /**
     * 获取当月任务
     * @return
     */
    List<DayNum> getMonthlySmsTasks();
}
