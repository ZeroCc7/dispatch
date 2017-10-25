package com.wlwx.dispatch.controller;

import com.wlwx.dispatch.entity.DayNum;
import com.wlwx.dispatch.entity.SmsTask;
import com.wlwx.dispatch.mapper.SmsTaskMapper;
import com.wlwx.dispatch.service.SmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/smstask")
public class SmsTaskController {

    @Autowired
    SmsTaskService smsTaskService;

    /**
     * 查询当月分组数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMonthlySmsTask")
    public List<DayNum> getMonthlySmsTask(){
        List<DayNum> dayNums = smsTaskService.getMonthlySmsTasks();
        return dayNums;
    }
}
