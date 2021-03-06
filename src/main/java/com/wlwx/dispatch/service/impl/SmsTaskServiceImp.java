package com.wlwx.dispatch.service.impl;

import com.wlwx.dispatch.entity.DayNum;
import com.wlwx.dispatch.entity.SmsTask;
import com.wlwx.dispatch.mapper.SmsMapper.SmsTaskMapper;
import com.wlwx.dispatch.service.SmsTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SmsTaskServiceImp implements SmsTaskService {

    @Resource
    SmsTaskMapper smsTaskMapper;


    @Override
    public List<SmsTask> getTodaySmsTasks() {
        return smsTaskMapper.getTodaySmsTasks();
    }

    @Override
    public List<DayNum> getMonthlySmsTasks() {
        return smsTaskMapper.getMonthlySmsTasks();
    }
}
