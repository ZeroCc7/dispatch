package com.wlwx.dispatch.service;


import com.wlwx.dispatch.entity.SmsTask;

import java.util.List;

public interface SmsTaskService {
    List<SmsTask> getTodaySmsTasks();
}
